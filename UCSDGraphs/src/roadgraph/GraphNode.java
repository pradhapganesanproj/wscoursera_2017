package roadgraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import geography.GeographicPoint;

/**
 * GraphNode.java
 * 
 * @author Pradhap Ganesan
 * 
 * A class to represent a Node with edges in a graph 
 */
public class GraphNode{

	private GeographicPoint geographicPoint = null;
	private List<GraphEdge> neighbors = new ArrayList<GraphEdge>();
	
	private Double distance = Double.POSITIVE_INFINITY;
	private Double heuristic = Double.POSITIVE_INFINITY;
	
	private Integer duration = Integer.MAX_VALUE;
	
	/*
	 * Constructor to create only a GraphNode, other properties defaults
	 */
	public GraphNode(GeographicPoint geographicPoint) {
		this.geographicPoint = geographicPoint;
	}

	//Overloaded
	public GraphNode(GeographicPoint geographicPoint,  List<GraphEdge> neighbors) {
		this.geographicPoint = geographicPoint;
		this.neighbors = neighbors;
	}
	/**
	 * addNeighbor add edge into List of Neighbors of graphNode
	 * 
	 * @edge already created edge obj add as a neighbor
	 */
	public void addNeighbor(GraphEdge edge){
		if(edge == null){
			return;
		}
		neighbors.add(edge);
	}
	
	/**
	 * Overloaded addNeighbor takes all required parameters to create GraphEdge
	 * start graphNode assumed to be this object
	 * 
	 * @toNode end GraphNode
	 * @roadName edge name of GraphEdge
	 * @roadType edge type of GraphEdge
	 * @length distance of GraphEdge
	 */
	public void addNeighbor(GraphNode toNode,String  roadName, String roadType, double length){
		if(toNode == null){
			return;
		}
		GraphEdge graphEdge = new GraphEdge(this, toNode, roadName, roadType, length);
		neighbors.add(graphEdge);
	}

	/**
	 * Overloaded addNeighbor takes all required parameters to create GraphEdge obj
	 * @fromNode starting GraphNode
	 * @toNode end GraphNode
	 * @roadName edge name of GraphEdge
	 * @roadType edge type of GraphEdge
	 * @length distance of GraphEdge
	 */
	public void addNeighbor(GraphNode fromNode, GraphNode toNode,String  roadName, String roadType, double length){
		if(toNode == null){
			return;
		}
		if(null == fromNode){
			fromNode = this;
		}
		GraphEdge graphEdge = new GraphEdge(fromNode, toNode, roadName, roadType, length);
		neighbors.add(graphEdge);
	}
	
	/*
	 * Override equals because GraphNode contains same geographicPoints are
	 * equal
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o2){
		if(null == o2 
				|| null == this
				|| !(o2 instanceof GraphNode)){
			return false;
		}
		GraphNode other = (GraphNode)o2;
		return other.hashCode() == this.hashCode() && this.getGeographicPoint().equals(other.getGeographicPoint());
	}
	
	/*
	 * override hashCode because GraphNode has same geographicPoint should return same
	 * hashcode
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return geographicPoint.hashCode();
	}
	
	public GeographicPoint getGeographicPoint() {
		return geographicPoint;
	}

	public void setGeographicPoint(GeographicPoint geographicPoint) {
		this.geographicPoint = geographicPoint;
	}

	public List<GraphEdge> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<GraphEdge> neighbors) {
		this.neighbors = neighbors;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(Double heuristic) {
		this.heuristic = heuristic;
	}
	
	
	public String toString(){
		return this.getGeographicPoint()+" Dist: "+this.distance + " Duratn: "+this.getDuration() +" Heuristic: "+this.getHeuristic();
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
