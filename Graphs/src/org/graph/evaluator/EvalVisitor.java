/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
package org.graph.evaluator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.antlr.evaluator.*;
import org.graph.ui.Coordinates;

public class EvalVisitor extends LabeledExprBaseVisitor<Double> {
    
	/** "memory" for our calculator; variable/value pairs go here */
    Map<String, Double> memory = new HashMap<String, Double>();
    List<Coordinates> coordinatesList = new ArrayList<Coordinates>();
    
    /** ID '=' expr NEWLINE */
    @Override
    public Double visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText();  // id is left-hand side of '='
        double value = Double.valueOf(ctx.DOUBLE().getText());
        //double value = visit(ctx.expr());   // compute value of expression on right
        System.out.println("Inserting .. "+id+" value = "+value);
        memory.put(id, value);           // store it in our memory
        return value;
    }
    
    /** expr '=' expr NEWLINE **/
    @Override 
    public Double visitEvaluate(LabeledExprParser.EvaluateContext ctx) { 
    	System.out.println("Left Expresion "+ctx.expr(0).getText()+" and : "+ctx.expr(0));
    	System.out.println("Right Expresion "+ctx.expr(1).getText()+" and : "+ctx.expr(1).getRuleContext());
    	Double expression = visit(ctx.expr(0)); // evaluate the expr child
    	Double value = visit(ctx.expr(1));
    	System.out.println("Called .."+expression+" and "+value);
    	if(Double.compare(expression, value) == 0){
    		
    		Iterator it = memory.entrySet().iterator();
    	    while (it.hasNext()) {
    	        Map.Entry pair = (Map.Entry)it.next();
    	        System.out.println("Printing : "+pair.getKey() + " = " + pair.getValue());
    	    }
    	    double x=0.0,y=0.0;
    	    if(memory.containsKey("x") && memory.containsKey("y")){
    	    	x = memory.get("x");
    	    	System.out.println("Contains x : "+x);
    	    	coordinatesList.add(new Coordinates(memory.get("x"),memory.get("y")));
    	    }
    	}
    	return 0.0; 
    }

    /** expr NEWLINE */
    @Override
    public Double visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr()); // evaluate the expr child
        //System.out.println(value);         // print the result
        return 0.0;                          // return dummy value
    }

    /** INT */
    @Override
    public Double visitDouble(LabeledExprParser.DoubleContext ctx)  {
        return Double.valueOf(ctx.DOUBLE().getText());
    }
    
    /** INT */
    @Override
    public Double visitInt(LabeledExprParser.IntContext ctx) {
    	return Double.valueOf(ctx.INT().getText());
    }

    /** ID */
    @Override
    public Double visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if ( memory.containsKey(id) ) return memory.get(id);
        return 0.0;
    }

    /** expr op=('*'|'/'|'^') expr */
    @Override
    public Double visitMulDivPow(LabeledExprParser.MulDivPowContext ctx) { 
        double left = visit(ctx.expr(0));  // get value of left subexpression
        double right = visit(ctx.expr(1)); // get value of right subexpression
        if ( ctx.op.getType() == LabeledExprParser.MUL ) return left * right;
        if ( ctx.op.getType() == LabeledExprParser.DIV )return left / right; // must be DIV
        return Math.pow(left,right);
    }

    /** expr op=('+'|'-') expr */
    @Override
    public Double visitAddSub(LabeledExprParser.AddSubContext ctx) {
        double left = visit(ctx.expr(0));  // get value of left subexpression
        double right = visit(ctx.expr(1)); // get value of right subexpression
        if ( ctx.op.getType() == LabeledExprParser.ADD ) return left + right;
        return left - right; // must be SUB
    }

    /** '(' expr ')' */
    @Override
    public Double visitParens(LabeledExprParser.ParensContext ctx) {
        return visit(ctx.expr()); // return child expr's value
    }
    
    public List<Coordinates> getCoordinatesList(){
    	return coordinatesList;
    }
}
