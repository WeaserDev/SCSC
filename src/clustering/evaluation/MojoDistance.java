package clustering.evaluation;

public class MojoDistance extends SupervisedEvaluation {
	int authoritativeClusterNumber;
	int clusterNumber;
	
	public MojoDistance(int[] authoritativeClusters) {
		super(authoritativeClusters);
		}

	
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		authoritativeClusterNumber = clusterNumber(authoritativeClusters);
		clusterNumber = clusterNumber(clusters);
		int[][] clustersIntersection = new int[clusterNumber][authoritativeClusterNumber];
		int [] maxIntersection = new int[clusterNumber];
		int maxIntersectionSum = 0;
		for (int i=0;i<clusters.length;i++) {
			clustersIntersection[clusters[i]][authoritativeClusters[i]]+=1;
		}
		for (int i=0;i<clusterNumber;i++) {
			for (int k=0;k<authoritativeClusterNumber;k++) {
				if (maxIntersection[i]<clustersIntersection[i][k]) maxIntersection[i]=clustersIntersection[i][k];
			}
			maxIntersectionSum+=maxIntersection[i];
		}
		int nonEmptyGroupsNumber = maxBipartiteMatching(clustersIntersection, maxIntersection);

		return clusters.length - maxIntersectionSum + clusterNumber - nonEmptyGroupsNumber;
	}
	
	boolean bipartiteMatching(int clustersIntersection[][],int maxIntersection[], int u, boolean checked[], int matchR[]) { 
	    // Try every cluster one by one 
	    for (int i = 0; i < authoritativeClusterNumber; i++) { 
	        // If cluster u and i has max intersection
	        // and i isn't checked yet 
	        if (clustersIntersection[u][i]==maxIntersection[u] && !checked[i]){       
	            // Mark i as checked 
	            checked[i] = true;  
	
	            // If authoritative cluster i is not assigned to 
	            // an automatic cluster OR previously 
	            // assigned automatic cluster for authoritative cluster i (which 
	            // is matchR[v]) has an alternate cluster available. 
	            // Since v is marked as checked in the  
	            // above line, matchR[v] in the following 
	            // recursive call will not get cluster i again 
	            if (matchR[i] < 0 || bipartiteMatching(clustersIntersection,maxIntersection, matchR[i], 
	                                     checked, matchR)) 
	            { 
	                matchR[i] = u; 
	                return true; 
	            } 
	        } 
	    } 
	    return false; 
	}
	int maxBipartiteMatching(int clustersIntersection[][], int maxIntersection[]) 
    { 
        // An array to keep track of the cluster assignments   
        // The value of matchR[i] is the cluster assigned to cluster i  
        // the value -1 indicates nobody is assigned. 
        int matchR[] = new int[authoritativeClusterNumber]; 
  
        // Initially all clusters are available 
        for(int i = 0; i < authoritativeClusterNumber; ++i) 
            matchR[i] = -1; 
  
        // Count of clusters assigned
        int result = 0;  
        for (int k = 0; k < clusterNumber; k++) 
        { 
            // Mark all clusters as not checked  
            boolean checked[] =new boolean[authoritativeClusterNumber] ; 
            for(int i = 0; i < authoritativeClusterNumber; ++i) 
                checked[i] = false; 
  
            // Find if the cluster i can get assigned 
            if (bipartiteMatching(clustersIntersection, maxIntersection, k, checked, matchR)) 
                result++; 
        } 
        return result; 
    } 
	
	
	
	
	private int clusterNumber(int[] clusters) {
		int max = clusters[0];
		for (int i=1; i < clusters.length ; i++) {
			if (clusters[i]>max) {
				max=clusters[i];
			}
		}
		max+=1;
		return max;
	}

}
