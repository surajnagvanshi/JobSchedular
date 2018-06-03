import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Stack;


public class StringHandler {
    private static String correctingStringExpression(String expression) {
        LinkedList<String> stringAtFirstNode=new LinkedList<String>();
        stringAtFirstNode.add(0,expression);

        for(int counter=1;counter<((stringAtFirstNode.get(0).length())-1);counter++)
        {
            if(stringAtFirstNode.get(0).charAt(counter)=='(')
            {
                if(stringAtFirstNode.get(0).charAt(counter)!='('&&(stringAtFirstNode.get(0).charAt(counter-1)!='+'&&stringAtFirstNode.get(0).charAt(counter-1)!='-'&&stringAtFirstNode.get(0).charAt(counter-1)!='*'&&stringAtFirstNode.get(0).charAt(counter-1)!='/'))
                {
                    stringAtFirstNode.set(0,stringAtFirstNode.get(0).substring(0,counter)+"*"+stringAtFirstNode.get(0).substring(counter,stringAtFirstNode.get(0).length()));
                    counter++;
                }
            }
            else if(stringAtFirstNode.get(0).charAt(counter)==')')
            {
                if(stringAtFirstNode.get(0).charAt(counter+1)!=')'&&(stringAtFirstNode.get(0).charAt(counter+1)!='+'&&stringAtFirstNode.get(0).charAt(counter+1)!='-'&&stringAtFirstNode.get(0).charAt(counter+1)!='*'&&stringAtFirstNode.get(0).charAt(counter+1)!='/'))
                {
                    stringAtFirstNode.set(0,stringAtFirstNode.get(0).substring(0,counter+1)+"*"+stringAtFirstNode.get(0).substring(counter+1,stringAtFirstNode.get(0).length()));
                    counter++;
                }
            }

        }
        stringAtFirstNode.set(0,('('+stringAtFirstNode.get(0)+')'));
        return (stringAtFirstNode.get(0));
    }
    private static LinkedList stringToExpression(String expression) {
        LinkedList<String> linkedList=new LinkedList();
        for(int counter=0;counter<expression.length();counter++)
        {
            if(expression.charAt(counter)=='+'||expression.charAt(counter)=='-'||expression.charAt(counter)=='*'||expression.charAt(counter)=='/')
            {
                linkedList.add("operator");
                linkedList.add(Character.toString(expression.charAt(counter)));
            }
            else if(expression.charAt(counter)=='0'||expression.charAt(counter)=='1'||expression.charAt(counter)=='2'||expression.charAt(counter)=='3'||expression.charAt(counter)=='4'||expression.charAt(counter)=='5'||expression.charAt(counter)=='6'||expression.charAt(counter)=='7'||expression.charAt(counter)=='8'||expression.charAt(counter)=='9')
            {
                int initial=counter;
                while(expression.charAt(counter)=='0'||expression.charAt(counter)=='1'||expression.charAt(counter)=='2'||expression.charAt(counter)=='3'||expression.charAt(counter)=='4'||expression.charAt(counter)=='5'||expression.charAt(counter)=='6'||expression.charAt(counter)=='7'||expression.charAt(counter)=='8'||expression.charAt(counter)=='9')
                {
                    counter++;
                }
                linkedList.add("number");
                linkedList.add(expression.substring(initial,counter));
                counter--;
            }
            else if(expression.charAt(counter)=='(')
            {
                linkedList.add("startBracket");
                linkedList.add(Character.toString(expression.charAt(counter)));
            }
            else if(expression.charAt(counter)==')')
            {
                linkedList.add("endBracket");
                linkedList.add(Character.toString(expression.charAt(counter)));
            }

        }
        return (linkedList);
    }
    private static LinkedList<String> correctNegativeNumbers(LinkedList<String> linkedList){
        ListIterator<String> listIterator=linkedList.listIterator();
        while(listIterator.hasNext())
        {
            if(listIterator.next().equals("("))
            {
                if(listIterator.next().equals("operator")) {
                    if(listIterator.next().equals("-"))
                    {
                        System.out.println("InHere");
                        listIterator.previous();
                        listIterator.remove();
                        listIterator.previous();
                        listIterator.remove();
                        listIterator.next();
                        String string=listIterator.next();
                        listIterator.remove();
                        listIterator.add("-"+string);
                    }
                }

            }
        }
        return linkedList;
    }
    public static String operate(LinkedList<String> linkedList) {
        Stack<String> stack=new Stack<String>();
        ListIterator<String> listIterator=linkedList.listIterator();
        while(listIterator.hasNext())
        {
            String string=listIterator.next();
            if (!(string.equals("-")||string.equals("+")||string.equals("*")||string.equals("/")))
                stack.push(string);
            else
            {
                long first,second;
                second=Long.parseLong(stack.pop());
                first=Long.parseLong(stack.pop());
                if(string.equals("+"))
                    stack.push(Long.toString(first+second));
                else if(string.equals("-"))
                    stack.push(Long.toString(first-second));
                else if(string.equals("*"))
                    stack.push(Long.toString(first*second));
                else if(string.equals("/"))
                    stack.push(Long.toString(first/second));

            }
        }
        return stack.pop();
    }
    private static LinkedList<String> postfix(LinkedList<String> linkedList) {
        Stack<String> stringStack=new Stack<String>();
        LinkedList<String> expression=new LinkedList<String>();
        LinkedList<String> expressionDescription=new LinkedList<String>();
        LinkedList<String> postfix=new LinkedList<String>();
        ListIterator<String> listIterator=linkedList.listIterator();
        while(listIterator.hasNext())
        {
            expressionDescription.add(listIterator.next());
            expression.add(listIterator.next());
        }
        ListIterator<String> expresionIterator=expression.listIterator();
        ListIterator<String> expresionDescIterator=expressionDescription.listIterator();
        while(expresionIterator.hasNext()&&expresionDescIterator.hasNext())
        {
            String description=expresionDescIterator.next();
            String exp=expresionIterator.next();
            if(description.equals("startBracket"))
            {
                stringStack.push(exp);
            }
            else if(description.equals("number"))
                postfix.add(exp);
            else if(description.equals("operator"))
            {
                String currentOperator=exp;
                if(!stringStack.isEmpty()) {
                    String temp =stringStack.pop();
                    while ((!temp.equals("("))&&(temp.equals("+") || temp.equals("-") || temp.equals("*") || temp.equals("/"))) {
                        if (operatorPrecedence(temp) == operatorPrecedence(currentOperator) || operatorPrecedence(temp) > operatorPrecedence(currentOperator)) {
                            postfix.add(temp);
                        }
                        else
                        {
                            break;
                        }
                        temp = stringStack.pop();
                    }
                    stringStack.push(temp);
                    stringStack.push(currentOperator);
                }
            }
            else if(description.equals("endBracket")&&!stringStack.isEmpty())
            {
                String temp=stringStack.pop();
                while(!temp.equals("("))
                {
                    postfix.add(temp);
                    temp=stringStack.pop();
                }
            }
        }
        return postfix;
    }
    private static int operatorPrecedence(String operator) {
        if(operator.equals("+")||operator.equals("-"))
            return 1;
        if(operator.equals("*")||operator.equals("/"))
            return 2;
        return 0;
    }
    public static int[] burstTime(String jobs[],int noOfJobs){
        int []burstTime=new int[noOfJobs];
        for(int counter=0;counter<noOfJobs;counter++){
            int temp=0;
            LinkedList<String> expression=correctNegativeNumbers(stringToExpression(correctingStringExpression(jobs[counter])));
            ListIterator<String> listIterator=expression.listIterator();
            while(listIterator.hasNext()){
                if((listIterator.next()).equals("operator")){
                    temp++;
                }
            }
            burstTime[counter]=temp;
        }
        return burstTime;
    }
    public static Stack<String> postfix(String expression){
        LinkedList<String> postfixList= StringHandler.postfix(StringHandler.correctNegativeNumbers(StringHandler.stringToExpression(StringHandler.correctingStringExpression(expression))));
        Stack<String> postfixStack=new Stack<String>();
        ListIterator<String> listIterator=postfixList.listIterator();
        while(listIterator.hasNext()){
            postfixStack.push(listIterator.next());
        }
        return (postfixStack);
    }
    public static LinkedList singleOperation(Stack<String> postfix){
        int counter=0;
        String first,second,result="0";

        LinkedList singleOperation=new LinkedList();
        while(!postfix.isEmpty()){
            String operator=postfix.get(counter);
            if(operator.equals("+")||operator.equals("/")||operator.equals("*")||operator.equals("-")) {
                postfix.remove(counter);
                second = postfix.remove(counter-1);
                first = postfix.remove(counter-2);
                if (operator.equals("+"))
                    result = Long.toString(Long.parseLong(first) + Long.parseLong(second));
                else if (operator.equals("-"))
                    result = Long.toString(Long.parseLong(first) - Long.parseLong(second));
                else if (operator.equals("*"))
                    result = Long.toString(Long.parseLong(first) * Long.parseLong(second));
                else if (operator.equals("/"))
                    result = Long.toString(Long.parseLong(first) / Long.parseLong(second));
                postfix.add(counter-2,result);
                singleOperation.addLast(first);
                singleOperation.addLast(operator);
                singleOperation.addLast(second);
                singleOperation.addLast(result);
                singleOperation.addLast(postfix);

                return singleOperation;

            }
            counter++;


        }
        singleOperation.addFirst("0.0");
        return singleOperation;
    }
    public static String stackToString(Stack<String> expression){
        ListIterator<String> iterator=expression.listIterator();
        //to store changing string at 0 location
        LinkedList<String> tempList=new LinkedList<>();
        tempList.addFirst(" ");
        while(iterator.hasNext()){
            tempList.addFirst(tempList.get(0)+" "+iterator.next());
        }
        return tempList.get(0);
    }

}
