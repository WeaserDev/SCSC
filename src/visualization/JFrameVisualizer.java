package visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dataImport.FileInput;
import dataImport.ProjectInput;

public class JFrameVisualizer {
	private ProjectInput project;
	private HashMap<Integer, ArrayList<FileInput>> fileClusters = new HashMap<Integer, ArrayList<FileInput>>();
	public JFrameVisualizer(int[] clusters, ProjectInput project) {
		this.project = project;
		for(int i=0;i<clusters.length;i++) {
			if(!fileClusters.containsKey(clusters[i]))
				fileClusters.put(clusters[i], new ArrayList<FileInput>());
			fileClusters.get(clusters[i]).add(project.getInput()[i]);
		}
	}

	public void visualize() {
		JFrame frame = new JFrame(project.getProjectName());
		frame.setSize(1200, 1000);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.add(new VisualizationPanel());
	}
	
	class VisualizationPanel extends JPanel {
		private static final long serialVersionUID = -6488159125622768948L;
		private ArrayList<Integer> prevPosX = new ArrayList<Integer>();
		private ArrayList<Integer> prevPosY = new ArrayList<Integer>();
		private ArrayList<Integer> prevR = new ArrayList<Integer>();

		public boolean isValid(int posX, int posY, int r) {
			for(int i=0;i<prevR.size();i++)
				if((posX-prevPosX.get(i))*(posX-prevPosX.get(i))+(posY-prevPosY.get(i))*(posY-prevPosY.get(i))<(prevR.get(i)/2+r/2+20)*(prevR.get(i)/2+r/2+20))
						return false;
			return true;
		}
		
		@Override
		public void paint(Graphics g) {
			if(!tryToPaint(g, 20))
				if(!tryToPaint(g, 20))
					if(!tryToPaint(g, 20))
						if(!tryToPaint(g, 20))
							tryToPaint(g, 10);
		}
		private boolean tryToPaint(Graphics g, int rMultiplier) {
			g.clearRect(0, 0, getWidth(), getHeight());
			prevPosX.clear();
			prevPosY.clear();
			prevR.clear();
			for(ArrayList<FileInput> fileCluster : fileClusters.values()) {
				int r = fileCluster.size()*rMultiplier;
				int posX = (int)(Math.random()*(getWidth()-r))+r/2;
				int posY = (int)(Math.random()*(getHeight()-r))+r/2;
				int count = 0;
				while(!isValid(posX,posY,r)) {
					posX = (int)(Math.random()*1000)+100;
					posY = (int)(Math.random()*800)+100;
					count++;
					if(count>500)
						return false;
				}
				prevPosX.add(posX);
				prevPosY.add(posY);
				prevR.add(r);
				g.setColor(Color.yellow);
				g.fillOval(posX-r/2, posY-r/2, r, r);
				g.setColor(Color.black);
				g.drawOval(posX-r/2, posY-r/2, r, r);
				for(FileInput file : fileCluster) {
					g.drawString(file.getFileName(), posX-r/2, posY-r/2+15);
					posY += 15;
				}
			}
			return true;
		}
	}

}