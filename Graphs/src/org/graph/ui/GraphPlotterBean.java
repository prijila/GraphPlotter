package org.graph.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.graph.evaluator.EvalVisitor;
import org.antlr.evaluator.LabeledExprLexer;
import org.antlr.evaluator.LabeledExprParser;

@ManagedBean
@ViewScoped
public class GraphPlotterBean implements Serializable{

	private String expression;
	private List<Coordinates> dataList;
	private String lineChartData;
	
	public GraphPlotterBean() {
		lineChartData = "";
	}
	
	public void plotGraph(){
		 try {
			dataList = getGraphData(expression);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(dataList != null && !dataList.isEmpty()){
			populateData();
		}
	}
	
	private void populateData() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Coordinates data : dataList) {
			stringBuilder.append("[");
			//stringBuilder.append("'");
			stringBuilder.append(data.getxValue());
			//stringBuilder.append("'");
			stringBuilder.append(",");
			stringBuilder.append(data.getyValue());
			stringBuilder.append("]");
			stringBuilder.append(",");
		}
		lineChartData = stringBuilder.toString().substring(0,stringBuilder.toString().length() - 1);
		System.out.println("Line chart Data : "+lineChartData);
	}
	
	public List<Coordinates> getGraphData(String expression) throws IOException{
		List<Coordinates> dataList = new ArrayList<Coordinates>();
		System.out.println("GraPh data delegate called...");
		if(expression != null){
			if (expression != null) {
				StringBuilder sb = new StringBuilder();
				for (double x = -10; x <= 0; x++) {
					for (double y = -10; y <= 0; y++) {
						sb.append("x = " + x + " \n");
						sb.append("y = " + y + " \n");
						sb.append(expression + " \n");
					}
				}
				for (double x = 0; x <= 10; x++) {
					for (double y = -10; y <= 0; y++) {
						sb.append("x = " + x + " \n");
						sb.append("y = " + y + " \n");
						sb.append(expression + " \n");
					}
				}
				for (double x = 0; x <= 10; x++) {
					for (double y = 0; y <= 10; y++) {
						sb.append("x = " + x + " \n");
						sb.append("y = " + y + " \n");
						sb.append(expression + " \n");
					}
				}
				for (double x = -10; x <= 0; x++) {
					for (double y = 0; y <= 10; y++) {
						sb.append("x = " + x + " \n");
						sb.append("y = " + y + " \n");
						sb.append(expression + " \n");
					}
				}
				//InputStream is = new FileInputStream(inputFile);
				System.out.println("String = "+sb.toString());
				ANTLRInputStream input = new ANTLRInputStream(sb.toString());
				LabeledExprLexer lexer = new LabeledExprLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				LabeledExprParser parser = new LabeledExprParser(tokens);
				ParseTree tree = parser.prog(); // parse

				EvalVisitor eval = new EvalVisitor();
				eval.visit(tree);
				dataList = eval.getCoordinatesList();
				System.out.println("DataList found: "+dataList);
			}
		}
		
		return dataList;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}


	public List<Coordinates> getDataList() {
		return dataList;
	}


	public void setDataList(List<Coordinates> dataList) {
		this.dataList = dataList;
	}


	public String getLineChartData() {
		return lineChartData;
	}


	public void setLineChartData(String lineChartData) {
		this.lineChartData = lineChartData;
	}
	
}
