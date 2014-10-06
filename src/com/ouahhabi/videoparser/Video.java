package com.ouahhabi.videoparser;

import java.net.*;
import java.io.*;
import java.util.*;

public class Video
{
	protected URL _url;
	protected String _argument;
	protected String _videoLink;
	protected String _draft;
	protected String _title;
	protected String _artist;
	String _menuPage;

	public Video(String menuPage, String line)
	{
		_menuPage = menuPage;

		loadInfo(line);

		String address = menuPage + "index.php?vid=" + _argument + "&a=a";

		try
		{
			_url = new URL(address);
		}
		catch (Exception e)
		{
			System.err.println(e);
		}

		_draft = "empty";
	}

	@SuppressWarnings("resource")
	private void loadInfo(String line)
	{
		Scanner scan;
		String str, temp;

		str = line.replace('=', ' ').replace('&', ' ');
		scan = new Scanner(str);
		while (scan.hasNext())
			if (scan.next().equals("\"index.php?vid"))
			{
				_argument = scan.next();
				while (!(temp = scan.next()).equals("a"))
					_argument += "%20" + temp;

				break;
			}

		temp = "";
		while (scan.hasNext())
			temp += scan.next() + " ";

		getTrackInfo(temp);
	}

	@SuppressWarnings("resource")
	private void getTrackInfo(String line)
	{
		Scanner scan;
		String str, temp;

		str = line.replace('<', ' ').replace('>', ' ').replace("-", " - ");
		scan = new Scanner(str);

		scan.next();
		_artist = scan.next();
		while (scan.hasNext() && !(temp = scan.next()).equals("-")
				&& !(temp = scan.next()).equals("/a"))
			_artist += ' ' + temp;

		_title = "";
		while (scan.hasNext() && !(temp = scan.next()).equals("/a"))
			_title += ' ' + temp;
	}

	private void getInfo(String arg)
	{
		String temp = arg.replace("_-_", " - ");
		String[] res = temp.split("-");
		_artist = "";
		for (int i = 0; i < res.length - 1; i++)
			_artist += res[i].replace('_', ' ');

		if (res.length < 2)
			_title = "Untitled";
		else
			_title = res[res.length - 1].replace('_', ' ');
	}

	private String getArgument(String line)
	{
		String s = line.replace('=', ' ');
		s = s.replace('&', ' ');
		Scanner scan = new Scanner(s);
		String in, arg = "";

		while (scan.hasNext())
		{
			in = scan.next();
			if (in.equals("\"index.php?vid"))
			{
				arg = scan.next();
				break;
			}
		}

		scan.close();
		return arg;
	}

	public void extractVideoLink()
	{
		BufferedReader input = null;

		try
		{
			input = new BufferedReader(new InputStreamReader(_url.openStream()));
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
		String line = "";

		while (line != null)
		{
			try
			{
				line = input.readLine();
			}
			catch (Exception e)
			{
				System.err.println(e);
			}

			if (line.equals("menu=\"false\""))
			{
				try
				{
					_draft = input.readLine();
				}
				catch (Exception e)
				{
					System.err.println(e);
				}

				break;
			}
		}

		try
		{
			input.close();
		}
		catch (Exception e)
		{
			System.err.println(e);
		}
	}

	public void processDraft()
	{
		String in = "", s = _draft.replace('=', ' ');
		s = s.replace('"', ' ');
		s = s.replace('&', ' ');
		s = s.replace("..", " ");

		Scanner scan = new Scanner(s);

		while (!in.equals("file"))
			in = scan.next();

		_videoLink = scan.next();
		_videoLink = "http://www.90smusicvidz.com" + _videoLink;
		scan.close();
	}

	public String getDraft()
	{
		return _draft;
	}

	public String getVideoLink()
	{
		return _videoLink;
	}

	@Override
	public String toString()
	{
		return _artist + " - " + _title;
	}
}
