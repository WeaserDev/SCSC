package featureExtraction.dimensionReduction;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.io.IOException;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class LSI {
	int cutoffPoint;
	Array2DRowRealMatrix initialTable;
	float[][] finalTable;
	double[][] truncatedU;
	double[][] truncatedS;
	double[][] truncatedVT;
	double[][] truncatedUT;
	double[][] truncatedV;
	/**
	 * This is the basic constructor of the LSA class, the cutoffPoint has to be manually selected
	 */
	public LSI(float occurenceTable[][], int cutoffPoint) {
		double table[][] = new double[occurenceTable.length][occurenceTable[0].length];
		for (int i = 0 ; i < occurenceTable.length; i++){
			for (int k=0;k<occurenceTable[0].length;k++){	
			    table[i][k] = (double) occurenceTable[i][k];				
			}
		}
		this.initialTable = new Array2DRowRealMatrix(table);
		this.cutoffPoint = cutoffPoint;
		if (this.cutoffPoint>occurenceTable.length) {
			this.cutoffPoint = occurenceTable.length;
		}
		if(this.cutoffPoint>occurenceTable[0].length) {
			this.cutoffPoint = occurenceTable[0].length;
		}
		calculateTables();
	}
	/**
	 * This constructor creates an LSA object setting the cutoff point to (m*n)^0.2, where m and n are the dimensions of the occurenceTable
	 */
	public LSI(float occurenceTable[][]) {
		double table[][] = new double[occurenceTable.length][occurenceTable[0].length];
		for (int i = 0 ; i < occurenceTable.length; i++){
			for (int k=0;k<occurenceTable[0].length;k++){	
			    table[i][k] = (double) occurenceTable[i][k];				
			}
		}
		this.initialTable = new Array2DRowRealMatrix(table);
		double rank = Math.pow(occurenceTable.length*occurenceTable[0].length, 0.2);
		this.cutoffPoint = (int) Math.round(rank);
		if (this.cutoffPoint>occurenceTable.length) {
			this.cutoffPoint = occurenceTable.length;
		}
		if(this.cutoffPoint>occurenceTable[0].length) {
			this.cutoffPoint = occurenceTable[0].length;
		}
		calculateTables();
	}
	/**
	 * Returns a matrix with the same dimensions as the original but its rank is reduced to the cutoffPoint
	 */
	public float[][] getLowRankApproximation() {
	    RealMatrix lowRankApproximation = (new Array2DRowRealMatrix(truncatedU)).multiply(new Array2DRowRealMatrix(truncatedS).multiply(new Array2DRowRealMatrix(truncatedVT)));
	    double[][] approximatedTable = lowRankApproximation.getData();	    
		return doubleMatrixToFloat(approximatedTable);
	}
	/**
	 * Returns the document-concept matrix, this matrix has the same rows as the initial but its columns represent the cutoffPoint largest singular values(hidden concepts)
	 */
	public float[][] getDocumentConceptTable(){
		RealMatrix fileConceptTable = (new Array2DRowRealMatrix(truncatedU)).multiply(new Array2DRowRealMatrix(truncatedS));
		double[][] approximatedTable = fileConceptTable.getData();
		return doubleMatrixToFloat(approximatedTable);
 	}
	/**
	 * Returns the concept-term matrix, this matrix has the same columns as the initial but its rows represent the cutoffPoint largest singular values(hidden concepts)
	 */
	public float[][] getConceptTermTable(){
		RealMatrix conceptTermTable = (new Array2DRowRealMatrix(truncatedS)).multiply(new Array2DRowRealMatrix(truncatedVT));
		double[][] approximatedTable = conceptTermTable.getData();
		return doubleMatrixToFloat(approximatedTable);
	}
	/**
	 * Returns the document-document matrix, both rows and columns of this matrix represent the documents
	 */
	public float[][] getDocumentDocumentTable(){
		RealMatrix documentDocumentTable = (new Array2DRowRealMatrix(truncatedU)).multiply(new Array2DRowRealMatrix(truncatedS).multiply(new Array2DRowRealMatrix(truncatedS).multiply(new Array2DRowRealMatrix(truncatedUT))));
		double[][] approximatedTable = documentDocumentTable.getData();
		return doubleMatrixToFloat(approximatedTable);
	}
	/**
	 * Returns the term-term matrix, both rows and columns of this matrix represent the terms
	 */
	public float[][] getTermTermTable(){
		RealMatrix termTermTable = (new Array2DRowRealMatrix(truncatedV)).multiply(new Array2DRowRealMatrix(truncatedS).multiply(new Array2DRowRealMatrix(truncatedS).multiply(new Array2DRowRealMatrix(truncatedVT))));
		double[][] approximatedTable = termTermTable.getData();
		return doubleMatrixToFloat(approximatedTable);
	}
	
	private void calculateTables() {
		SingularValueDecomposition svd = new SingularValueDecomposition(initialTable);
	    truncatedU = new double[svd.getU().getRowDimension()][cutoffPoint];
	    svd.getU().copySubMatrix(0, truncatedU.length - 1, 1, cutoffPoint - 1, truncatedU);
	    
	    truncatedUT = new double[cutoffPoint][svd.getUT().getColumnDimension()];
	    svd.getUT().copySubMatrix(1,cutoffPoint - 1 , 0, truncatedUT[0].length - 1, truncatedUT);

	    truncatedS = new double[cutoffPoint][cutoffPoint];
	    svd.getS().copySubMatrix(1, cutoffPoint - 1, 1, cutoffPoint - 1, truncatedS);
	    
	    truncatedVT = new double[cutoffPoint][svd.getVT().getColumnDimension()];
	    svd.getVT().copySubMatrix(1, cutoffPoint - 1, 0, truncatedVT[0].length - 1, truncatedVT);
	    
	    
	    truncatedV = new double[svd.getV().getRowDimension()][cutoffPoint];
	    svd.getV().copySubMatrix(0, truncatedV.length - 1, 1, cutoffPoint - 1 , truncatedV);
	}
	
	private float[][] doubleMatrixToFloat(double[][] doubleMatrix){
		float[][] floatMatrix = new float[doubleMatrix.length][doubleMatrix[0].length];
		for (int i = 0 ; i < doubleMatrix.length; i++){
			for (int k=0;k<doubleMatrix[0].length;k++){	
			    floatMatrix[i][k] = (float) doubleMatrix[i][k];				
			}
		}
		return floatMatrix;
	}
}
