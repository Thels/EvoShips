package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import arena.objects.AbstractShip;

public class ScorePanel extends JPanel 
{
	private int shipCount;
	private int[] values;
	private String[] shipNames;


	public ScorePanel(Dimension screenSize, ArrayList<AbstractShip> ships) 
	{
		this.shipCount = ships.size();
		values = new int[shipCount];
		shipNames = new String[shipCount];
		setSize(screenSize);
		setPreferredSize(screenSize);
		setMinimumSize(screenSize);

		for(int i = 0; i < shipCount ; i++)
		{
			values[i] = ships.get(i).getScore();
			shipNames[i] = ships.get(i).getShipName();
		}
	}


	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);

		if (values == null || values.length == 0)
			return;
		double minValue = 0;
		double maxValue = 0;
		for (int i = 0; i < values.length; i++) {
			if (minValue > values[i])
				minValue = values[i];
			if (maxValue < values[i])
				maxValue = values[i];
		}

		Dimension d = getSize();
		int clientWidth = d.width;
		int clientHeight = d.height;
		int barWidth = clientWidth / values.length;

		Font titleFont = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
		Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
		FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

		int titleWidth = titleFontMetrics.stringWidth("Scores");
		int y = titleFontMetrics.getAscent();
		int x = (clientWidth - titleWidth) / 2;
		g.setFont(titleFont);
		g.drawString("Scores", x, y);

		int top = titleFontMetrics.getHeight();
		int bottom = labelFontMetrics.getHeight();
		if (maxValue == minValue)
			return;
		double scale = (clientHeight - top - bottom) / (maxValue - minValue);
		y = clientHeight - labelFontMetrics.getDescent();
		g.setFont(labelFont);

		for (int i = 0; i < values.length; i++)
		{
			int valueX = i * barWidth + 1;
			int valueY = top;
			int height = (int) (values[i] * scale);
			if (values[i] >= 0)
				valueY += (int) ((maxValue - values[i]) * scale);
			else {
				valueY += (int) (maxValue * scale);
				height = -height;
			}

			g.setColor(Color.red);
			//g.fillRect(valueX, valueY - 20, barWidth - 80, height);
			g.setColor(Color.black);
			g.drawRect(valueX, valueY - 20, barWidth - 80, height);


			g.setColor(Color.blue);
			g.fillRect(valueX, valueY + 20, barWidth - 80, height-20 );
			g.setColor(Color.black);
			g.drawRect(valueX, valueY + 20, barWidth - 80, height-20 );
			int labelWidth = labelFontMetrics.stringWidth(shipNames[i]);
			x = i * barWidth + (barWidth - labelWidth) / 2;
			g.drawString(shipNames[i], x, y);
		}


	}
}