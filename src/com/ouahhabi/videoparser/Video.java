package com.ouahhabi.videoparser;

import java.net.*;
import java.io.*;
import java.util.*;

public class Video
{
	protected String _argument;
	protected String _videoLink;
	protected String _title;
	protected String _menuPage;

	public Video(String menuPage, String line)
	{
		_menuPage = menuPage;

		loadInfo(line);
	}

	@SuppressWarnings("resource")
	public void extractFLVAddress()
	{
		URL pageAddress = null;
		Scanner input = null;
		String line, temp;

		try
		{
			pageAddress = new URL(_menuPage + "?vid=" + _argument + "&a=a");
			input = new Scanner(new BufferedReader(new InputStreamReader(
					pageAddress.openStream())));
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		while (input.hasNextLine()
				&& !(temp = input.nextLine()).equals("menu=\"false\""))
			;

		line = input.nextLine();

		input.close();

		line = line.replace('=', ' ').replace('"', ' ').replace('&', ' ')
				.replace("..", " ");

		input = new Scanner(line);

		while (input.hasNext() && !(temp = input.next()).equals("file"))
			;

		_videoLink = input.next();

		while (input.hasNext() && !(temp = input.next()).equals("image"))
			_videoLink += "%20" + temp;

		_videoLink = _menuPage + _videoLink;

		System.out.println(_videoLink);

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

		_title = _argument.replace('_', ' ');
	}

	public String getVideoLink()
	{
		return _videoLink;
	}

	@Override
	public String toString()
	{
		return _title;
	}

	public String toXML()
	{
		return "<track><title>" + _title + "</title><location>" + _videoLink
				+ "</location></track>";
	}
}
