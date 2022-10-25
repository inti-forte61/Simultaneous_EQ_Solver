import java.io.PrintWriter;
import java.io.IOException;

public class Solver{

	public static double[] SKYLINE(double a[][],double y[]){

		int i,j,k,n;
		double s,t;

		int sc = a.length;
		double[][] l = new double[sc][sc];
		double[][] u = new double[sc][sc];
		double[][] lu = new double[sc][sc];
		double[] ux = new double[sc];
		double[] x = new double[sc];

		for (j=0;j<sc;j++){
			u[0][j] = a[0][j];
			l[j][0] = a[j][0]/u[0][0];
			l[j][j] = 1.0;
		}//j

		for (i=1;i<sc;i++){
			for (j=i;j<sc;j++){
				s = 0.;
				for (k=0;k<i;k++){
					s += l[i][k]*u[k][j];
				}
				u[i][j] = a[i][j] - s;
			}
			for (j=i+1;j<sc;j++){
				s = 0.;
				for (k=0;k<i;k++){
					s += l[j][k]*u[k][i];
				}
				l[j][i] = ( a[j][i] - s )/u[i][i];
			}
		}

		for (i=0;i<sc;i++){ // L x U check
			for (j=0;j<sc;j++){
				s = 0.;
				for (k=0;k<sc;k++){
					s += l[i][k]*u[k][j];
				}
				lu[i][j] = s;
			}
		}

		ux[0] = y[0];
		for (i=1;i<sc;i++){
			s = 0.;
			for (k=0;k<i;k++){
				s += l[i][k]*ux[k];
			}//k
			ux[i] = y[i] - s;
		}

		n = sc - 1;
		x[n]=ux[n]/u[n][n];
		for (i=1;i<sc;i++){
			s = 0.;
			for (k=1;k<i+1;k++){
				j = n-(i-k);
				s += u[n-i][n-(i-k)]*x[n-(i-k)];
			}//k
			x[n-i] = ( ux[n-i] - s )/u[n-i][n-i];
		}//i

		try{			
			PrintWriter pw_l = new PrintWriter("../results\\l_mat.dat");
			PrintWriter pw_u = new PrintWriter("../results\\u_mat.dat");
			PrintWriter pw_lu = new PrintWriter("../results\\lu_mat.dat");
		
			for (i=0;i<sc;i++){
				for (j=0;j<sc;j++){
					s = l[i][j];
					t = u[i][j];
					pw_l.format(String.format("%15.5f\t",s));
					pw_u.format(String.format("%15.5f\t",t));
				}
				pw_l.format("\n");
				pw_u.format("\n");
			}
			for (i=0;i<sc;i++){
				for (j=0;j<sc;j++){
					s = lu[i][j];
					pw_lu.format(String.format("%15.5f\t",s));
				}
				pw_lu.format("\n");
			}
			pw_l.close();
			pw_u.close();
			pw_lu.close();
		}catch(IOException e){
			System.out.println(e);
		}

		return x;
	}
}