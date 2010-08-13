package aco;

import  java.util.Vector ;
import  java.lang.Math ;
import  java.util.Random ;


public  class LPath2
{

	public  static Vector<Vector <Double>> adjacencyMatrix ;
	public  static Vector<Vector <Double>> pheromoneMatrix ;
	public  static Vector<Vector <Integer>> solution ;
	public  static Vector<Integer> bestSolution ;
	public  static Vector<Double> cost ;
	public  static double bestCost =0 ;
	public  static Vector<Boolean> visited ;
	public  static double counter=0;

	public  static double initialPheromone = 5 ;
	public  static int  ants = 10 ;
	public  static double evaporationRate = 0.5 ;
	public  static double depositRate = 2 ;
	public  static int  numberIteration = 1000 ;
	public  static int  size ;                                    //graph size	



	public  static  double alpha = 3 ;
	public  static  double beta = 1 ;


	public  static void initialize ()
	{
		Input.openFile ("longestPath.txt") ;
		Vector<Double> vc  = Input.readFile () ;
		Input.closeFile () ;
		size = (int)Math.sqrt(vc.size()-2);
		alpha = vc.elementAt(vc.size()-1) ;
		beta = vc.elementAt(vc.size()-2) ;
		

		/***************************************************/
		adjacencyMatrix = new Vector<Vector <Double>> ();
		pheromoneMatrix = new Vector<Vector <Double>> ();
		solution = new Vector<Vector <Integer>> ();
		bestSolution = new Vector<Integer>();
		cost = new Vector<Double>();
		visited = new Vector<Boolean> ();
		/***************************************************/


		/***************************************************/
		for ( int i=0 ; i<size ; i++ )
			adjacencyMatrix.add(new Vector<Double>());
		for ( int i=0 ; i<size ; i++ )
			pheromoneMatrix.add(new Vector<Double>());
		for ( int i=0 ; i<ants ; i++ )
			solution.add(new Vector<Integer>());
		/***************************************************/

		/****************************************************/		
		for ( int i=0 ; i<size ; i++ )
			for ( int j=0 ; j<size ; j++ )
				adjacencyMatrix.elementAt(i).add(vc.elementAt(i*size+j));

		for ( int i=0 ; i<size ; i++ )
			for ( int j=0 ; j<size ; j++ )
				if(adjacencyMatrix.elementAt(i).elementAt(j)>0)
					pheromoneMatrix.elementAt(i).add(initialPheromone);
				else
					pheromoneMatrix.elementAt(i).add(0.0);
		
		for ( int i=0 ; i<size ; i++ )
			visited.add(i,false);

		for ( int i=0 ; i<ants ; i++ )
			cost.add(i,0.0);
		/******************************************************/
	}



	public  static  void findMaximalPath(int  currentCT , int ant)
	{
		int counter = 0 ;
		double currentProbability  ;
		int currentProbabilityIndex = 0 ;
		double sum=0 ;

		for ( int i=0 ; i<size ; i++ )
			if(i != currentCT)
				if(adjacencyMatrix.elementAt(currentCT).elementAt(i)>0)
					if(!visited.elementAt(i))
					{
						sum += Math.pow(pheromoneMatrix.elementAt(currentCT).elementAt(i) , alpha)*Math.pow(adjacencyMatrix.elementAt(currentCT).elementAt(i) , beta) ;
						currentProbabilityIndex = i ;
						counter++ ;
					}

		if(counter == 0)
			return;

		counter = 0 ;
		currentProbability = Math.pow(pheromoneMatrix.elementAt(currentCT).elementAt(currentProbabilityIndex),alpha)*Math.pow(adjacencyMatrix.elementAt(currentCT).elementAt(currentProbabilityIndex),beta)/sum ;
		
		for ( int i=0 ; i<size ; i++ )
		{
			
			if(adjacencyMatrix.elementAt(currentCT).elementAt(i)>0)
			{

				if(!visited.elementAt(i))
				{
					double temp = Math.pow(pheromoneMatrix.elementAt(currentCT).elementAt(i),alpha)*Math.pow(adjacencyMatrix.elementAt(currentCT).elementAt(i),beta)/sum ;
					if(temp > currentProbability)
					{
						currentProbability = temp ;
						currentProbabilityIndex = i ;			
					}
			
					counter++ ;
						
					if(temp > Math.random() )
					{
						visited.remove(i);
						visited.add(i,true);
						solution.elementAt(ant).add(i);
						double tempCost = cost.elementAt(ant) + adjacencyMatrix.elementAt(currentCT).elementAt(i);
						cost.remove(ant);
						cost.add(ant,tempCost);
						findMaximalPath(i,ant);
						return;
					}
				}

			}
			
		}

		if(counter != 0)
		{
			visited.remove(currentProbabilityIndex);
			visited.add(currentProbabilityIndex,true);
			solution.elementAt(ant).add(currentProbabilityIndex);
			double tempCost = cost.elementAt(ant) + adjacencyMatrix.elementAt(currentCT).elementAt(currentProbabilityIndex);
			cost.remove(ant);
			cost.add(ant,tempCost);
			findMaximalPath(currentProbabilityIndex,ant);
		}	
	}


	public static  void  pheromoneUpdate ()
	{
		
		int vertex1 ;
		int vertex2 ;

		for ( int i=0 ; i<size ; i++ )
		{
			for (  int j=0 ; j<size ; j++ )
			{
				double temp = pheromoneMatrix.elementAt(i).elementAt(j) ;
				temp = (1-evaporationRate)*temp ;
				pheromoneMatrix.elementAt(i).remove(j);
				pheromoneMatrix.elementAt(i).add(j,temp);
			}
		}

		int currentBestSolutionSize = bestSolution.size();

		for ( int i=0 ; i<(currentBestSolutionSize-1) ; i++ )
		{
			vertex1 = bestSolution.elementAt(i);
			vertex2 = bestSolution.elementAt(i+1);
			double temp = pheromoneMatrix.elementAt(vertex1).elementAt(vertex2) + depositRate ;
			pheromoneMatrix.elementAt(vertex1).remove(vertex2) ;
			pheromoneMatrix.elementAt(vertex1).add(vertex2,temp) ;
			
		}

		
	}

	public  static  void  findLongestPath()
	{
			

			for ( int i=0 ; i<ants ; i++ )
			{
				for ( int j=0 ; j<size ; j++ )
				{
					visited.remove(j);
					visited.add(j,false);
				}
			
				Random  rg = new Random ();
				int currentCT = rg.nextInt(size);
				visited.remove(currentCT);
				visited.add(currentCT,true);
				solution.elementAt(i).add(currentCT);

				findMaximalPath(currentCT,i);
			}

			double tempCost=cost.elementAt(0) ;
			int tempCostIndex=0 ;
			for ( int i=1 ; i<ants ; i++ )
			{
				if(cost.elementAt(i) > tempCost)
				{
					tempCost = cost.elementAt(i);
					tempCostIndex = i ;
				}
			}

			if(tempCost>bestCost)
			{
				bestCost = tempCost ;
				if(!bestSolution.isEmpty())
					bestSolution.clear();
				int currentBestSolutionSize = solution.elementAt(tempCostIndex).size();
				for ( int j=0 ; j<currentBestSolutionSize ; j++ )
					bestSolution.add (solution.elementAt(tempCostIndex).elementAt(j));
				
				
			}

			pheromoneUpdate();
		
			

		
	}

	public  static  void  main ( String  args[] )
	{
		
		initialize();
		for ( int i=0 ; i<numberIteration ; i++ )
		{
			for ( int j=0 ; j<ants ; j++ )
			{
				cost.remove(j);
				cost.add(j,0.0);
				solution.elementAt(j).removeAllElements();
			}
		
			findLongestPath();
		
			for ( int k=0 ; k<ants ; k++ )
			{
				int currentSolutionSize = solution.elementAt(k).size();
				for ( int j=0 ; j<currentSolutionSize ; j++ )
					System.out.printf("%d\t",solution.elementAt(k).elementAt(j));
				System.out.printf("%f",cost.elementAt(k));
				System.out.printf("\n");
			}
			System.out.printf("\n");
			
			int currentBestSolutionSize = bestSolution.size();
			
			for ( int k=0 ; k<currentBestSolutionSize ; k++ )
				System.out.printf("%d\t",bestSolution.elementAt(k));
			System.out.printf("\n");
			System.out.printf("cost:%f",bestCost);
			System.out.printf("\n");
			System.out.printf("\n");
		}



		System.out.printf("\n\n");

		for ( int i=0 ; i<size ; i++ )
		{
			for( int j=0 ; j<size ; j++ )
				System.out.printf("%f\t",pheromoneMatrix.elementAt(i).elementAt(j));	
			System.out.printf("\n");
		}	
		
	}
}

