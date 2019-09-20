package featureExtraction.dimensionReduction;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class LSA {
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
	public LSA(float occurenceTable[][], int cutoffPoint) {
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
	 * This constructor creates an LSA object setting the cutoff point appropriately to keep all singular values that are greater than 1, according to the Guttman-Kaiser criterion
	 */
	public LSA(float occurenceTable[][]) {
		double table[][] = new double[occurenceTable.length][occurenceTable[0].length];
		for (int i = 0 ; i < occurenceTable.length; i++){
			for (int k=0;k<occurenceTable[0].length;k++){	
			    table[i][k] = (double) occurenceTable[i][k];				
			}
		}
		this.initialTable = new Array2DRowRealMatrix(table);
		this.cutoffPoint = calculateCuttoffPointGuttmanCriterion();
		calculateTables();
	}
	/**
	 * This constructor creates an LSA object calculating the cutoff point so that at lease variancePercentage percent of the initial variance is retained
	 */
	public LSA(float occurenceTable[][], float variancePercentage) {
		double table[][] = new double[occurenceTable.length][occurenceTable[0].length];
		for (int i = 0 ; i < occurenceTable.length; i++){
			for (int k=0;k<occurenceTable[0].length;k++){	
			    table[i][k] = (double) occurenceTable[i][k];				
			}
		}
		this.initialTable = new Array2DRowRealMatrix(table);
		this.cutoffPoint = calculateCuttoffPointVariancePercentage(variancePercentage);
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
	    svd.getU().copySubMatrix(0, truncatedU.length - 1, 0, cutoffPoint - 1, truncatedU);
	    
	    truncatedUT = new double[cutoffPoint][svd.getUT().getColumnDimension()];
	    svd.getUT().copySubMatrix(0,cutoffPoint - 1 , 0, truncatedUT[0].length - 1, truncatedUT);

	    truncatedS = new double[cutoffPoint][cutoffPoint];
	    svd.getS().copySubMatrix(0, cutoffPoint - 1, 0, cutoffPoint - 1, truncatedS);
	    
	    truncatedVT = new double[cutoffPoint][svd.getVT().getColumnDimension()];
	    svd.getVT().copySubMatrix(0, cutoffPoint - 1, 0, truncatedVT[0].length - 1, truncatedVT);
	    
	    
	    truncatedV = new double[svd.getV().getRowDimension()][cutoffPoint];
	    svd.getV().copySubMatrix(0, truncatedV.length - 1, 0, cutoffPoint - 1 , truncatedV);
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
	private int calculateCuttoffPointGuttmanCriterion() {
		SingularValueDecomposition svd = new SingularValueDecomposition(initialTable);
		double[][] S = svd.getS().getData();
		int k=0;
		
		for (int i=0; i<S.length;i++) {
			if(S[i][i]<1) {
				k=i;
				break;
			}
		}
		if (k==0) {
			k = S.length;
		}
		return k;
	}
	private int calculateCuttoffPointVariancePercentage(float variancePercentage) {
		SingularValueDecomposition svd = new SingularValueDecomposition(initialTable);
		double[][] S = svd.getS().getData();
		int k=0;
		float totalSum =0;
		float currentSum =0;
		for (int i=0; i<S.length;i++) {
			totalSum += Math.pow(S[i][i], 2);
		}
		totalSum = (float) Math.sqrt(totalSum);
		for (int i=0; i<S.length;i++) {
			currentSum += Math.pow(S[i][i],2);
			if((Math.sqrt(currentSum)/totalSum)*100 > variancePercentage) {
				k=i+1;
				break;
			}
		}		
		return k;
	}
	public int getCutoffPoint() {
		return this.cutoffPoint;
	}
}
