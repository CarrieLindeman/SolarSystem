/*******************************
 * NBody Simulation - visually represent solar system using Newtonian PhysicsCar 


 * @author Carrie Lindeman
 * 2/25/14
 * 
 */


import java.io.FileInputStream;
public class NBody {

	public static void main(String[] args) throws Exception{

        //plays music for opening
        //StdAudio.play("2001.mid");
		
		// read in planet information
		System.setIn(new FileInputStream("planets.txt"));
	// number of planets
		int N = StdIn.readInt();
		// radius of universe
		double R = StdIn.readDouble();
		// Gravitational Constant
		double G = 6.67e-11;
		// time step
		double dt = 25000; //time delta
		
		//graphic info
		StdDraw.setXscale(-R, R);
        StdDraw.setYscale(-R, R);
        StdDraw.picture(0, 0, "starfield.jpg",2*R,2*R);

		//planet graphic array
		String[] planetGraphics = new String[N];
		
		//planet force array
		double[][][] forceArray = new double[N][N][2];
		
		//planet info array (2d)
		double[][] planetInfo = new double[N][5];
		//fills in array with planet info from txt file
		for (int p = 0;p<N;p++){
			for(int i = 0;i<5;i++){
				planetInfo[p][i]=StdIn.readDouble();//gets numerical info for double array
			}
			planetGraphics[p]=StdIn.readString();//gets graphic name for string array
		}
		
		//draw images in universe
		for (int im = 0;im<N;im++){
			StdDraw.picture(planetInfo[im][0], planetInfo[im][1], planetGraphics[im]);
		}
		
		
		//loop for all of the calculating and animating
		int t = 0;
		while(true){
			
			//CALCULATE THE FORCES AND STORE THEM
			for(int i=0;i<N;i++){//loop for main planet
				double forceX=0;
				double forceY=0;
				double mPlanet = planetInfo[i][4];
			for(int j=0;j<N;j++){//loop for planet comparison
				if(i==j){continue;}
				double moPlanet = planetInfo[j][4];
				double x = planetInfo[j][0]-planetInfo[i][0];
				double y = planetInfo[j][1]-planetInfo[i][1];
				
				double r = Math.sqrt((x*x) + (y*y));
					double gravForce = (G*mPlanet*moPlanet)/(r*r);
					forceX = gravForce*(x/r);
					forceY = gravForce*(y/r);
				//calculates net force
				forceArray[i][j][0]=forceX;
				forceArray[i][j][1]=forceY;
				//resets force x and y
				forceX=0;
				forceY=0;
				}
			}
			
			//CALCULATE THE VELOCITY AND GET NEW POSITION
			for(int i = 0;i<N;i++){
				double netForceX=0;
				double netForceY=0;
				//sums up forces saved in array so you get net forces for one planet
				for(int c=0;c<N;c++){
					netForceX+=forceArray[i][c][0];
					netForceY+=forceArray[i][c][1];
					}
				double vx = planetInfo[i][2];
				double vy = planetInfo[i][3];
				double mPlanet = planetInfo[i][4];
			//calculates acceleration
				double accelX=netForceX/mPlanet;
				double accelY=netForceY/mPlanet;
			//calculates and replaces velocity in x and y direction
				vx=vx+(dt*accelX);
				vy=vy+(dt*accelY);
				planetInfo[i][2]=vx;
				planetInfo[i][3]=vy;
			//calculates and replaces position in x and y
				double posX=planetInfo[i][0];
				double posY=planetInfo[i][1];
				planetInfo[i][0]=posX+(vx*dt);
				planetInfo[i][1]=posY+(vy*dt);
				posX=planetInfo[i][0];
				posY=planetInfo[i][1];
			}
			
			
			//replaces images
			StdDraw.picture(0, 0, "starfield.jpg");
			for (int im = 0;im<N;im++){
				StdDraw.picture(planetInfo[im][0], planetInfo[im][1], planetGraphics[im]);
			}
			StdDraw.show(10);
			
			//OPTIONAL prints edited arrays
			/*
			for (int p = 0;p<N;p++){
				for(int i = 0;i<5;i++){
					System.out.format("%6.3e   ",planetInfo[p][i]);
				}
				System.out.format("%s  \n",planetGraphics[p]);
			}
			System.out.println();
			*/
			t+=dt;
		}
				
	}

}
