/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	//contains Vertices in the HashMap
	Map<GeographicPoint,GraphNode> nodeAdjList;
	//List consists only Edge (GraphEdge)
	List<GraphEdge> edgeAdjList;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		nodeAdjList = new HashMap<GeographicPoint,GraphNode>();
		edgeAdjList = new ArrayList<GraphEdge>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return null != this.nodeAdjList?nodeAdjList.size():0;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return nodeAdjList.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return null != edgeAdjList ? edgeAdjList.size():0;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if(null == location || null == nodeAdjList 
				|| nodeAdjList.keySet().contains(location)){
			return false;
		}
		nodeAdjList.put(location,new GraphNode(location));
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3

		//throws IllegalArgumentException for invalid inputs
		validateInputForAddEdge(from, to, length);
		
		GraphNode fromGraphNode = nodeAdjList.get(from);
		GraphNode toGraphNode = nodeAdjList.get(to);
		
		GraphEdge edge = new GraphEdge(fromGraphNode,toGraphNode, roadName, roadType, length);

		fromGraphNode.addNeighbor(edge);
		edgeAdjList.add(edge);
	}

	/*
	 * private validateInputForAddEdge method is to validate the input parameters are passed to "public addEdge"  method.
	 * 
	 * throws IllegalArgumentException If the points have not already been added
	 * as nodes to the graph, if any of the arguments is null, or if the length
	 * is less than 0.
	 */
	private void validateInputForAddEdge(GeographicPoint from, GeographicPoint to, double length) {
		if(null == from 
				|| null == to 
				|| ! nodeAdjList.keySet().contains(from)
				|| ! nodeAdjList.keySet().contains(to)
				|| length < 0){
			throw new IllegalArgumentException();
		}
	}

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		//to keep track of visited nodes, to avoid duplicate processes.
		Set<GraphNode> visited = new HashSet<GraphNode>();
		//Queue is to achieve BFS, and explore nodes one by one horizontally (breadth)  
		Queue<GraphNode>  toExplore = new LinkedList<GraphNode>();
		//Keep track of route start to goal
		Map<GraphNode,GraphNode> path = new HashMap<GraphNode, GraphNode>();
		//Ordered start to goal GeographicPoint's path
		List<GeographicPoint> pathList = null;
		
		//Basic initialize requires before the loop
		GraphNode goalNode = nodeAdjList.get(goal); 
		GraphNode startNode = nodeAdjList.get(start);
		toExplore.add(startNode);
		visited.add(startNode);
		path.put(startNode, null);
		
		while(!toExplore.isEmpty()){
			//remove from front of queue
			GraphNode currNode = toExplore.remove();

			// Hook for visualization.  See writeup.
			nodeSearched.accept(currNode.getGeographicPoint());
			
			//end the loop when reach the goal
			if(currNode.equals(goalNode)){
				break;
			}
			
			//Get linked neighbor to current GraphNode/GeographicPoint
			List<GraphEdge> neighbors = currNode.getNeighbors();
			
			for(GraphEdge edge:neighbors){
				GraphNode toNode = edge.getTo();

				//if neighbor node is NOT already visited, then proceed
				if(!visited.contains(toNode)){
					//add node to visited, so never visit again.
					visited.add(toNode);
					//add node to the queue, to explore in order.
					toExplore.add(toNode);
					//To keep track of path, explore node and parent node added or override with new parent. 
					path.put(toNode,currNode);
				}
			}
			
		}
		//creates list of ordered GeographPoint, only when any path found from start to goal.
		pathList = constructPath(startNode, goalNode, path);
		
		return pathList;
	}


	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		if(start == null || null == goal || !nodeAdjList.containsKey(start) || !nodeAdjList.containsKey(goal)){
			return null;
		}
		
		Comparator<GraphNode> distanceCompare = MapGraph::dijkstraDistanceComparator; 
		PriorityQueue<GraphNode> toExplore = new PriorityQueue<GraphNode>(distanceCompare);

		Set<GraphNode> visited = new HashSet<GraphNode>();
		Map<GraphNode, GraphNode> path = new HashMap<GraphNode,GraphNode>();
		
		GraphNode goalN = new GraphNode(goal,nodeAdjList.get(goal).getNeighbors());
		GraphNode startN = new GraphNode(start,nodeAdjList.get(start).getNeighbors());
		
		path.put(startN, null);

		startN.setDistance(0.0);
		startN.setDuration(0);
		toExplore.add(startN);
		int visitCount = 0;
		while(! toExplore.isEmpty()){
			GraphNode currN = toExplore.remove();
			
			if(visited.contains(currN)){
				continue;
			}
			
			System.out.println("DIJKSTRA visiting[NODE at location "+currN.getGeographicPoint());
			currN.getNeighbors().forEach(e->System.out.println(" roadName: "+e.getRoadName()+ " roadType: "+e.getRoadType()));
			System.out.println();
			
			// Hook for visualization.  See writeup.
			nodeSearched.accept(currN.getGeographicPoint());

			visitCount++;
			visited.add(currN);
			
			if(currN.getGeographicPoint().equals(goal)){
				System.out.println("Nodes visited in search: "+visitCount+"\n");
				break; }

			Double currDist = currN.getDistance();
			Integer currDur = currN.getDuration();
			
			for(GraphEdge edge : currN.getNeighbors()){
				GraphNode toNode = new GraphNode(edge.getTo().getGeographicPoint(),edge.getTo().getNeighbors());
				if(!visited.contains(toNode)){
					//if path(toNode.length) through currN.distance to toNode is shorter
					if(edge.getLength()+currDist<toNode.getDistance()){
						toNode.setDistance(edge.getLength()+currDist);
						toNode.setDuration(edge.getSpeedLimit()+currDur);
						path.put(toNode, currN);
						toExplore.add(toNode);
					}
				}
			}
		}

		List<GeographicPoint>  shortestPathGP = constructPath(startN, goalN,path);
		
		return shortestPathGP;
	}
	
	/**
	 * comparator is to pass in PriorityQueue to sort based on Distance; use by dijkstra alg
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private static int dijkstraDistanceComparator(GraphNode from, GraphNode to){
		return from.getDistance().compareTo(to.getDistance());
	}
	
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstraDuration(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        //Short duration
        return dijkstraDurationImpl(start, goal, temp);
        
	}
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstraDurationImpl(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		if(start == null || null == goal || !nodeAdjList.containsKey(start) || !nodeAdjList.containsKey(goal)){
			return null;
		}
		
		Comparator<GraphNode> durationCompare = MapGraph::dijkstraDistanceComparator; 
		PriorityQueue<GraphNode> toExplore = new PriorityQueue<GraphNode>(durationCompare);

		Set<GraphNode> visited = new HashSet<GraphNode>();
		Map<GraphNode, GraphNode> path = new HashMap<GraphNode,GraphNode>();
		
		GraphNode goalN = new GraphNode(goal,nodeAdjList.get(goal).getNeighbors());
		GraphNode startN = new GraphNode(start,nodeAdjList.get(start).getNeighbors());
		
		path.put(startN, null);
		startN.setDistance(0.0);
		startN.setDuration(0);
		toExplore.add(startN);
		int visitCount = 0;
		while(! toExplore.isEmpty()){
			GraphNode currN = toExplore.remove();
			
			if(visited.contains(currN)){
				continue;
			}
			
			System.out.println("DIJKSTRA visiting[NODE at location "+currN.getGeographicPoint());
			currN.getNeighbors().forEach(e->System.out.println(" roadName: "+e.getRoadName()+ " roadType: "+e.getRoadType()+ " speed: "+e.getSpeedLimit()));
			System.out.println();
			
			// Hook for visualization.  See writeup.
			nodeSearched.accept(currN.getGeographicPoint());

			visitCount++;
			visited.add(currN);
			
			if(currN.getGeographicPoint().equals(goal)){
				System.out.println("Nodes visited in search: "+visitCount+"\n");
				break; }

			Integer currDur = currN.getDuration();
			Double currDist = currN.getDistance();
			
			for(GraphEdge edge : currN.getNeighbors()){
				GraphNode toNode = new GraphNode(edge.getTo().getGeographicPoint(),edge.getTo().getNeighbors());
				if(!visited.contains(toNode)){
					//if path(toNode.length) through currN.distance to toNode is shorter
					if(edge.getSpeedLimit()+currDur>=toNode.getDuration() || edge.getSpeedLimit()+currDur<Integer.MAX_VALUE){
						toNode.setDistance(edge.getLength()+currDist);
						toNode.setDuration(edge.getSpeedLimit()+currDur);
						path.put(toNode, currN);
						toExplore.add(toNode);
					}
				}
			}
		}

		List<GeographicPoint>  shortestPathGP = constructPath(startN, goalN,path);
		
		return shortestPathGP;
	}
	
	private static int dijkstraSpeedComparator(GraphNode gn1, GraphNode gn2){
		int durationCompare = gn1.getDuration().compareTo(gn2.getDuration());
		return  durationCompare== 0? gn1.getDistance().compareTo(gn2.getDistance()):durationCompare;
	}
	

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		if(start == null || null == goal || !nodeAdjList.containsKey(start) || !nodeAdjList.containsKey(goal)){
			return null;
		}
		
		//Create comparator reference
		Comparator<GraphNode> distanceCompare = MapGraph::aStarSearchComparator; 
		//PriorityQueue uses Min-Heap binary data structure to keep lower distance in top to remove first 
		PriorityQueue<GraphNode> toExplore = new PriorityQueue<GraphNode>(distanceCompare);

		Set<GraphNode> visited = new HashSet<GraphNode>();
		Map<GraphNode, GraphNode> path = new HashMap<GraphNode,GraphNode>();
		
		GraphNode goalN = new GraphNode(goal,nodeAdjList.get(goal).getNeighbors());
		GraphNode startN = new GraphNode(start,nodeAdjList.get(start).getNeighbors());
		
		path.put(startN, null);
		//default value for startNode
		startN.setHeuristic(0.0);
		startN.setDistance(0.0);
		toExplore.add(startN);
		int visitCount = 0;
		while(! toExplore.isEmpty()){
			GraphNode currN = toExplore.remove();
			if(visited.contains(currN)){
				continue;
			}
			
			System.out.println("A* visiting [NODE at location "+currN.getGeographicPoint());
			currN.getNeighbors().forEach(e->System.out.println(" roadName: "+e.getRoadName()+ " roadType: "+e.getRoadType()));
			System.out.println("Actual = "+currN.getDistance()+" Pred: "+currN.getHeuristic()+"\n");

			// Hook for visualization.  See writeup.
			nodeSearched.accept(currN.getGeographicPoint());
			
			visitCount++;
			visited.add(currN);
			
			if(currN.getGeographicPoint().equals(goal)){
				System.out.println("Nodes visited in search: "+visitCount+"\n");
				break; }

			Double currDist = currN.getDistance();
			
			for(GraphEdge edge : currN.getNeighbors()){
				GraphNode toNode = new GraphNode(edge.getTo().getGeographicPoint(),edge.getTo().getNeighbors());
				if(!visited.contains(toNode)){
					//if path(toNode.length) through currN.distance to toNode is shorter
					if(edge.getLength()+currDist<toNode.getDistance()){
						//set distance value to be considered to sort
						toNode.setDistance(edge.getLength()+currDist);
						//set Heuristic value to be considered to sort
						toNode.setHeuristic(calculateHeuristic(toNode.getDistance(),toNode, goalN));
						
						path.put(toNode, currN);
						toExplore.add(toNode);
					}
				}
			}
		}
		List<GeographicPoint>  shortestPathGP = constructPath(startN, goalN,path);
		
		return shortestPathGP;
	}

	/**
	 * calculate heuristic (straight line) estimation between start and goal vertices. 
	 * 
	 * @param startN
	 * @param goalN
	 * @return
	 */
	private Double calculateHeuristic(Double gn, GraphNode startN, GraphNode goalN) {
		double lat1 = startN.getGeographicPoint().getX();
		double lon1 = startN.getGeographicPoint().getY();
		double lat2 = goalN.getGeographicPoint().getX();
		double lon2 = goalN.getGeographicPoint().getY();
		
    	int R = 6373; // radius of the earth in kilometres
    	double lat1rad = Math.toRadians(lat1);
    	double lat2rad = Math.toRadians(lat2);
    	double deltaLat = Math.toRadians(lat2-lat1);
    	double deltaLon = Math.toRadians(lon2-lon1);

    	double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
    	        Math.cos(lat1rad) * Math.cos(lat2rad) *
    	        Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    	double hn = R * c;
    	
    	Double fn = gn + hn;
    	
    	return fn;
	}

	/***
	 * a comparator implementation for A star search
	 * compares vertices on heuristic
	 * 
	 * @param from
	 * @param to
	 * @return int -1 lesser ; 0 equal ; 1 greater;
	 */
	private static int aStarSearchComparator(GraphNode from, GraphNode to){
		return from.getHeuristic().compareTo(to.getHeuristic());
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarDurationSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarDurationSearchImpl(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarDurationSearchImpl(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		if(start == null || null == goal || !nodeAdjList.containsKey(start) || !nodeAdjList.containsKey(goal)){
			return null;
		}
		
		//Create comparator reference
		Comparator<GraphNode> distanceCompare = MapGraph::aStarSearchComparator; 
		//PriorityQueue uses Min-Heap binary data structure to keep lower distance in top to remove first 
		PriorityQueue<GraphNode> toExplore = new PriorityQueue<GraphNode>(distanceCompare);

		Set<GraphNode> visited = new HashSet<GraphNode>();
		Map<GraphNode, GraphNode> path = new HashMap<GraphNode,GraphNode>();
		
		GraphNode goalN = new GraphNode(goal,nodeAdjList.get(goal).getNeighbors());
		GraphNode startN = new GraphNode(start,nodeAdjList.get(start).getNeighbors());
		
		path.put(startN, null);
		//default value for startNode
		startN.setHeuristic(0.0);
		startN.setDistance(0.0);
		startN.setDuration(0);
		
		toExplore.add(startN);
		int visitCount = 0;
		while(! toExplore.isEmpty()){
			GraphNode currN = toExplore.remove();
			if(visited.contains(currN)){
				continue;
			}
			
			System.out.println("A* visiting [NODE at location "+currN.getGeographicPoint());
			currN.getNeighbors().forEach(e->System.out.println(" roadName: "+e.getRoadName()+ " roadType: "+e.getRoadType()));
			System.out.println("Actual = "+currN.getDistance()+" Pred: "+currN.getHeuristic()+"\n");

			// Hook for visualization.  See writeup.
			nodeSearched.accept(currN.getGeographicPoint());
			
			visitCount++;
			visited.add(currN);
			
			if(currN.getGeographicPoint().equals(goal)){
				System.out.println("Nodes visited in search: "+visitCount+"\n");
				break; }

			Double currDist = currN.getDistance();
			Integer currDur = currN.getDuration();
			
			for(GraphEdge edge : currN.getNeighbors()){
				GraphNode toNode = new GraphNode(edge.getTo().getGeographicPoint(),edge.getTo().getNeighbors());
				if(!visited.contains(toNode)){
					//if path(toNode.length) through currN.distance to toNode is shorter
					//if(edge.getLength()+currDist<toNode.getDistance()){
					if(edge.getSpeedLimit()+currDur>=toNode.getDuration() || edge.getSpeedLimit()+currDur<Integer.MAX_VALUE){
						//set distance value to be considered to sort
						toNode.setDuration(edge.getSpeedLimit()+currDur);
						toNode.setDistance(edge.getLength()+currDist);
						//set Heuristic value to be considered to sort
						toNode.setHeuristic(calculateHeuristic(toNode.getDistance(),toNode, goalN));
						
						path.put(toNode, currN);
						toExplore.add(toNode);
					}
				}
			}
		}
		List<GeographicPoint>  shortestPathGP = constructPath(startN, goalN,path);
		return shortestPathGP;
	}	
	
	/***
	 * a comparator implementation for A star search
	 * compares vertices on heuristic
	 * 
	 * @param from
	 * @param to
	 * @return int -1 lesser ; 0 equal ; 1 greater;
	 */
	private static int aStarSpeedSearchComparator(GraphNode from, GraphNode to){
		int speedCompare = from.getDuration().compareTo(to.getDuration());
		return speedCompare==0?from.getHeuristic().compareTo(to.getHeuristic()):speedCompare;
	}
	
	/**
	 * 
	 * travelSalesperson takes set of vertices as input and find path randomly
	 * selected startNode, and visit all other nodes and end in StartNode again.
	 * 
	 * @param setOfVertices
	 * @return
	 */
	public List<GeographicPoint> travelSalesperson(Set<GeographicPoint> setOfVertices) {
		List<GeographicPoint> travelSalespersonPath = null;
		
		Iterator<GeographicPoint> iter = setOfVertices.iterator();

		while(travelSalespersonPath==null && iter.hasNext()){
			GeographicPoint startPoint = iter.hasNext()?iter.next():null;
			travelSalespersonPath = travelSalespersonDijkstra(startPoint, setOfVertices);			
		}
		
		return travelSalespersonPath;
	}
	/**
	 * find path from startPoint and visit all other vertices
	 * this method only return path, if the endNode (visited last) has link to startNode
	 * 
	 * @param startPoint
	 * @param setOfVertices
	 * @return
	 */
	private List<GeographicPoint> travelSalespersonDijkstra(GeographicPoint startPoint, Set<GeographicPoint> setOfVertices){
		List<GeographicPoint> travelSalespersonPath = null;
		
		Comparator<GraphNode> distanceCompare = MapGraph::dijkstraDistanceComparator; 
		PriorityQueue<GraphNode> toExplore = new PriorityQueue<GraphNode>(distanceCompare);

		Set<GraphNode> visited = new HashSet<GraphNode>();
		Set<GeographicPoint> visitGeoPoint = new HashSet<GeographicPoint>(setOfVertices);
		Map<GraphNode, GraphNode> path = new HashMap<GraphNode,GraphNode>();
		
		GraphNode startN = new GraphNode(startPoint,nodeAdjList.get(startPoint).getNeighbors());
		GraphNode endN = null; 
		
		path.put(startN, null);

		startN.setDistance(0.0);
		startN.setDuration(0);
		toExplore.add(startN);
		int visitCount = 0;
		while(! toExplore.isEmpty() && visitGeoPoint.size()!=0){
			GraphNode currN = toExplore.remove();
			
			if(visited.contains(currN)){
				continue;
			}

			visitGeoPoint.remove(currN.getGeographicPoint());
			
			System.out.println("DIJKSTRA visiting[NODE at location "+currN.getGeographicPoint());
			currN.getNeighbors().forEach(e->System.out.println(" roadName: "+e.getRoadName()+ " roadType: "+e.getRoadType()));
			System.out.println();
			
			visitCount++;
			visited.add(currN);
			
			Double currDist = currN.getDistance();
			Integer currDur = currN.getDuration();
			
			for(GraphEdge edge : currN.getNeighbors()){
				GraphNode toNode = new GraphNode(edge.getTo().getGeographicPoint(),edge.getTo().getNeighbors());
				if(!visited.contains(toNode)){
					//if path(toNode.length) through currN.distance to toNode is shorter
					if(edge.getLength()+currDist<toNode.getDistance()){
						toNode.setDistance(edge.getLength()+currDist);
						toNode.setDuration(edge.getSpeedLimit()+currDur);
						path.put(toNode, currN);
						toExplore.add(toNode);
						//ref of last node
						endN = toNode;
					}
				}
			}
		}
		/*
		 * endN is reference to most recent node added into the Path. construct
		 * path from start and visit all node then end in start node
		 */
		travelSalespersonPath = constructPathTSP(startN, endN,path);
		
		return travelSalespersonPath;
	}

	/**
	 * private constructPath is a helper method, loops through path map and 
	 * returns a list of geographic points along the path ordered from start to goal.
	 *  
	 * @start starting node
	 * @goal destination node
	 * @path  Map<GraphNode, GraphNode> key is currentNode, value is Parent Node
	 */
	private List<GeographicPoint> constructPath(GraphNode start, 
			GraphNode goal,
			Map<GraphNode, GraphNode> path) {
		
		if(null == start || null == goal || !path.containsKey(goal) || !path.containsKey(start)){
			return null;
		}
		
		LinkedList<GeographicPoint> pathList = null;

		if (path.containsKey(goal)) {
			pathList = new LinkedList<GeographicPoint>();

			pathList.add(goal.getGeographicPoint());

			GraphNode parentNode = path.get(goal);
			
			while (parentNode != null && !parentNode.getGeographicPoint().equals(start.getGeographicPoint())) {
				pathList.addFirst(parentNode.getGeographicPoint());
				parentNode = path.get(parentNode);
			}

			pathList.addFirst(start.getGeographicPoint());
		}
		return pathList;
	}
	
	/**
	 * private constructPathTSP is a helper method, loops through path map and 
	 * returns a list of geographic points along the path ordered from start and visit all nodes and end in start node.
	 *  
	 * @start starting node
	 * @goal destination node
	 * @path  Map<GraphNode, GraphNode> key is currentNode, value is Parent Node
	 */
	private List<GeographicPoint> constructPathTSP(GraphNode start, 
			GraphNode end,
			Map<GraphNode, GraphNode> path) {
		
		if(null == start || null == end || !path.containsKey(end) || !path.containsKey(start)){
			return null;
		}
		LinkedList<GeographicPoint> pathList = null;

		if (path.containsKey(end)) {
			pathList = new LinkedList<GeographicPoint>();
			
			boolean completePath = isTSPPathComplete(start, end, pathList);
			
			//completePath is false then not found path to start node
			if(!completePath){
				return null;}

			pathList.add(end.getGeographicPoint());

			GraphNode parentNode = path.get(end);
			
			while (parentNode != null && !parentNode.getGeographicPoint().equals(start.getGeographicPoint())) {
				pathList.addFirst(parentNode.getGeographicPoint());
				parentNode = path.get(parentNode);
			}
			pathList.addFirst(start.getGeographicPoint());
		}
		return pathList;
	}

	/**
	 * check if endNode has connection to Start node.
	 * 
	 * 
	 * @param start
	 * @param end
	 * @param pathList
	 * @return boolean true: end graphNode if connected to startNode, otherwise false. 
	 */
	private boolean isTSPPathComplete(GraphNode start, GraphNode end, LinkedList<GeographicPoint> pathList) {
		boolean completePath = false;
		List<GraphEdge> endNeighbor = end.getNeighbors();
		Iterator<GraphEdge> endNeighborIter = endNeighbor.iterator();
		while(endNeighborIter.hasNext()){
			GraphEdge currEdgeEndNeighbor = endNeighborIter.next();
			GraphNode currNEndNeighbor = currEdgeEndNeighbor.getTo();
			if(currNEndNeighbor.equals(start)){
				completePath = true;
				//add endof path is start of the node itself
				pathList.add(start.getGeographicPoint());
				break;
			}
		}
		
		return completePath;
	}
	
	
	public static void main(String[] args)
	{    
//		List<GeographicPoint> testroute = null;
//		List<GeographicPoint> testroute2 = null;
		
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
//		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute = simpleTestMap.travelSalesperson(simpleTestMap.getVertices());
//		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);

//		List<GeographicPoint> testroute3 = simpleTestMap.dijkstraDuration(testStart,testEnd);
//		List<GeographicPoint> testroute4 = simpleTestMap.aStarDurationSearch(testStart,testEnd);
		
		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.travelSalesperson(simpleTestMap.getVertices());
//		testroute2 = testMap.dijkstraDuration(testStart,testEnd);
		/*testroute2 = testMap.aStarSearch(testStart,testEnd);
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);*/
		}
}
