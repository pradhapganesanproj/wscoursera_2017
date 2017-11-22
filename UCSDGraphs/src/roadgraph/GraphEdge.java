package roadgraph;

import java.util.HashMap;
import java.util.Map;

/*
 * 
 * GraphEdge class to represent edges between GraphNode
 * 
 */
public class GraphEdge {

	Map<String,Integer> SPEED_LIMIT_MAP = new HashMap<String,Integer>();
	//Anonymous class gets initialized along with object/just like constructor
	{
		SPEED_LIMIT_MAP.put("connector",70);
		
		SPEED_LIMIT_MAP.put("primary",65);
		SPEED_LIMIT_MAP.put("secondary",55);
		SPEED_LIMIT_MAP.put("tertiary",45);
		
		SPEED_LIMIT_MAP.put("city street",30);
		SPEED_LIMIT_MAP.put("residential",25);
		SPEED_LIMIT_MAP.put("living_street",20);
		
		SPEED_LIMIT_MAP.put("unclassified",35);

	}
	
	private String roadName = "";
	private String roadType = "";
	private double length = 0.0;	
	private GraphNode from = null;
	private GraphNode to = null;
	
	private int speedLimit;
	
	/**
	 * constructor of GraphEdge 
	 * constructs the object liking from and to GraphNodes 
	 * with edge details.
	 * 
	 * @fromNode from GraphNode 
	 * @toNode to GraphNode
	 * @roadName road name
	 * @roadType road tyep
	 * @length distance
	 * 
	 */
	public GraphEdge(GraphNode fromNode, GraphNode toNode,String  roadName, String roadType, double length) {
		this.from = fromNode;
		this.to = toNode;
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}

	public String getRoadName() {
		return roadName;
	}

	public String getRoadType() {
		return roadType;
	}

	public double getLength() {
		return length;
	}

	public GraphNode getFrom() {
		return from;
	}

	public GraphNode getTo() {
		return to;
	}

	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setFrom(GraphNode from) {
		this.from = from;
	}

	public void setTo(GraphNode to) {
		this.to = to;
	}
	
	public String toString(){
		return " roadName: "+this.roadName+" roadType: "+this.roadType+" length: "+this.getLength();
	}

	public int getSpeedLimit() {
		return SPEED_LIMIT_MAP.get(null != this.getRoadType()?this.getRoadType().toLowerCase():"unclassified");
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

}
