package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import arena.Arena;

public class ArenaFrame extends JFrame
{
	
	public ArenaFrame(Arena game, Dimension frameSize)
	{
		setVisible(true);
		ArenaPanel arenaPanel = new ArenaPanel(game, frameSize);
		setResizable(false);
		add(arenaPanel,BorderLayout.CENTER);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		game.run();
	}
	
	
}
