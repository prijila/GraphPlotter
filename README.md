# GraphPlotter using ANTL

Plotting graphs using ANTLR4

A given equation can be interpreted in a number of ways. The most obvious way is to design a lexer/parser to generate an abstract syntax tree. There are a variety of tools available that can help us to build this abstract syntax tree. In this project, I look at one such tool called ANTLR. The goal of my project is to understand how ANTLR works while developing an application. The aim of the application is to take in the equations given by the user, understand and interpret these equations and then generate a XY-axis graph corresponding to it.

For any given expression, we divide the expression into left and right sub-expressions. Further, we substitute values of x and y in the expression. When we have evaluated both sides of expressions, we compare to see if they match. If they do, then the substituted values of x and y are added to list of points that satisfies this equation.This list is then fed to Google Charts API and the result is displayed to the user.
