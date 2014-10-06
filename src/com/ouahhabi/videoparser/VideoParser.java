package com.ouahhabi.videoparser;

import java.net.*;
import java.io.*;
import java.util.*;

public class VideoParser
{
	protected String _menuPage;
	protected URL _url;
	protected ArrayList<Video> _videos;

	public VideoParser(String menuPage)
	{
		_menuPage = menuPage;
		try
		{
			_url = new URL(_menuPage);
		}
		catch (Exception e)
		{
			System.err.println(e);
		}

		_videos = new ArrayList<>();
		extractList();
	}

	public void extractList()
	{
		BufferedReader input = null;

		try
		{
			input = new BufferedReader(new InputStreamReader(_url.openStream()));
		}
		catch (Exception e)
		{
			System.err.println(e);
			System.exit(1);
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

			if (line.equals("</html>"))
				break;

			if (line.contains(" - <a target=\"_self\""))
			{
				_videos.add(new Video(_menuPage, line));
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

	public ArrayList<Video> getVideos()
	{
		return _videos;
	}
}