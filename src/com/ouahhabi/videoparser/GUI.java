package com.ouahhabi.videoparser;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class GUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private VideoParser _videoParser;
	private JPanel _contentPane;
	private JLabel _searchLabel;
	private JLabel _playlistLabel;
	private JLabel _messageLabel;
	private JTextField _searchField;
	private JTextField _playlistField;
	private JList<Video> _videosList;
	private JScrollPane _pane;
	private DefaultListModel<Video> _listModel;
	private JButton _generatePlaylist;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI frame = new GUI();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI()
	{
		setupWindow();
		loadComponents();
	}

	private void loadListModel()
	{
		_videoParser = new VideoParser("http://www.90smusicvidz.com");
		_listModel = new DefaultListModel<Video>();
		for (Video v : _videoParser.getVideos())
			_listModel.addElement(v);
		_videosList.setModel(_listModel);
	}

	private void setupWindow()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane
				.setLayout(new MigLayout("", "[]15[grow]15[]", "[]15[]15[grow]15[]"));
		setTitle("Visual video parser");

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
	}

	private void loadComponents()
	{
		_searchLabel = new JLabel("Search: ");
		_searchField = new JTextField(22);
		_playlistLabel = new JLabel("Playlist name: ");
		_playlistField = new JTextField(22);
		_messageLabel = new JLabel(
				"Select your desired songs from the list below. (CTRL+Click for mult selection)");
		_videosList = new JList<Video>();
		_generatePlaylist = new JButton("Generate VLC Playlist");

		loadListModel();

		_pane = new JScrollPane(_videosList);
		_contentPane.add(_searchLabel, "cell 0 0");
		_contentPane.add(_searchField, "cell 1 0 2 1, grow");
		_contentPane.add(_messageLabel, "cell 0 1 3 1 growx");
		_contentPane.add(_pane, "cell 0 2 3 4, grow");
		_contentPane.add(_playlistLabel, "cell 0 6");
		_contentPane.add(_playlistField, "cell 1 6, growx");
		_contentPane.add(_generatePlaylist, "cell 2 6");
	}
}
