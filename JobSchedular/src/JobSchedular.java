import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;
import static java.util.concurrent.TimeUnit.*;

public class JobSchedular{
    public static void main(String[] args) {
        JFrame frame = new JFrame("JOB Scheduler");
        (new FRame()).fRame(frame);
    }
}
class FRame{
    public void fRame(JFrame frame){
        //JFrame frame = new JFrame("JOB Scheduler");
        frame.setVisible(true);
        frame.setSize(800,500);
        frame.setMaximizedBounds(new Rectangle(800,500));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        new MyProcessors(frame);
        Container c=frame.getContentPane();
        c.setBackground(Color.CYAN);
    }
}

class StartPage{
    JFrame frame;int type;int tq;
    StartPage(JFrame frame,int type,int tq){
        this.frame=frame;this.type=type;this.tq=tq;
        startPage();
    }
    StartPage(JFrame frame,int type){
        this.frame=frame;this.type=type;this.tq=0;
        startPage();
    }

    private void startPage() {
        int[] settings=new int[4];

        JLabel contextSwitch=new JLabel("Time for context Switching");
        frame.add(contextSwitch);
        contextSwitch.setBounds(100,80,300,25);
        contextSwitch.setFont(new Font(contextSwitch.getText(), Font.PLAIN,20 ));
        String[] nos = {" 1 Second"," 2 Seconds"," 3 Seconds"," 4 Seconds"," 5 Seconds"," 6 Seconds"," 7 Seconds"," 8 Seconds"," 9 Seconds"," 10 Seconds"};
        JComboBox comboBox=new JComboBox(nos);
        frame.add(comboBox);
        comboBox.setBounds(350,80,100,25);

        JLabel cycleTime=new JLabel("Time of each machine cycle");
        frame.add(cycleTime);
        cycleTime.setBounds(100,130,300,25);
        cycleTime.setFont(new Font(cycleTime.getText(), Font.PLAIN,20 ));
        String[] ct = {" 2 Seconds"," 4 Seconds"," 6 Seconds"};
        JComboBox comboBox2=new JComboBox(nos);
        frame.add(comboBox2);
        comboBox2.setBounds(350,130,100,25);

        JLabel jobs=new JLabel("No. of Jobs to be executed");
        frame.add(jobs);
        jobs.setBounds(100,180,300,25);
        jobs.setFont(new Font(jobs.getText(), Font.PLAIN,20 ));
        String[] noJobs = {" 2 "," 3 "," 4 "," 5"," 6 "," 7 "," 8 "," 9 "," 10 "};
        JComboBox comboBox3=new JComboBox(noJobs);
        frame.add(comboBox3);
        comboBox3.setBounds(350,180,100,25);

        JButton proceed=new JButton("Proceed");
        frame.add(proceed);
        proceed.setBounds(660,410,100,30);
        new Restart(frame,360,510,410,410,100,100,30,30);
        proceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settings[0]=(comboBox.getSelectedIndex())+1;
                settings[1]=(comboBox2.getSelectedIndex())+1;
                settings[2]=(comboBox3.getSelectedIndex())+2;
                frame.remove(comboBox);frame.remove(comboBox2);frame.remove(comboBox3);
                frame.remove(cycleTime);frame.remove(contextSwitch);frame.remove(jobs);
                frame.repaint();
                settings[3]=1;
                frame.remove(proceed);
                frame.repaint();


                if(type==1){
                    new SetJobPage(frame,settings,type,tq);
                }

                if(type==2){
                    new SetJobPage(frame,settings,type,tq);
                }

                if(type==3){
                    new SetJobPage(frame,settings,type,tq);
                }

                if(type==4){
                    new SetJobPage(frame,settings,type,tq);
                }

                if(type==5){
                    new SetJobPageWithPriority(frame,settings,type);
                }

                if(type==6){
                    new SetJobPageWithPriority(frame,settings,type);
                }
            }
        });

        frame.setSize(800,500);
    }

}

class SetJobPage {
    SetJobPage(JFrame frame, int[] settings,int type,int tq){
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(900,600);
        int proceedPosition=0;

        String []hints=new String[10];
        hints[0]="2-3*4+5";
        hints[1]="(2-3)*(4+5)";
        hints[2]="(5+7*(6*7))+54-79";
        hints[3]="45+6-(567*6-4+56/3)";
        hints[4]="(45+56-34/2)/4*4";
        hints[5]="100-32*30/2+45*(56+345)";
        hints[6]="456*67-45/34*(45-56*67)";
        hints[7]="(45*5-(45+56-6/2*3)*2)/3*4+5";
        hints[8]="1+2-3/4*5+(1+2-3/4*5)*25/2";
        hints[9]="34-(4-5*2/4+100)*34/3";


        //Adding labels Jobs and Arrival Time
        JLabel jobs=new JLabel("Jobs");
        JLabel aT=new JLabel("Arrival Time");
        frame.add(jobs);
        frame.add(aT);
        jobs.setBounds(100,0,100,40);
        aT.setBounds(650,0,150,40);
        jobs.setFont(new Font("",Font.PLAIN,25));
        aT.setFont(new Font("",Font.PLAIN,25));



        //job numbering
        JLabel []labels=new JLabel[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            labels[counter]=new JLabel(" "+(counter+1));
            int y=(50+(counter*50));
            frame.add(labels[counter]);
            labels[counter].setBounds(50,y,100,40);
            (labels[counter]).setFont(new Font(labels[counter].getText(), Font.PLAIN,25 ));
            proceedPosition=y;
        }


        //job textfields
        JTextField []textFields=new JTextField[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            textFields[counter]=new JTextField(hints[counter]);
            int y=(50+(counter*50));
            frame.add(textFields[counter]);
            textFields[counter].setForeground(Color.gray);
            textFields[counter].addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    Object source=e.getSource();
                    int counter=0;
                    for(counter=0;counter<settings[2];counter++)
                    {
                        if(textFields[counter]==((JTextField)source))
                            break;
                    }
                    if(((JTextField)source).getText().equals(hints[counter])) {
                        ((JTextField) source).setText("");
                        ((JTextField)source).setForeground(Color.black);
                    }

                }

                @Override
                public void focusLost(FocusEvent e) {
                    Object source=e.getSource();
                    int counter=0;
                    for(counter=0;counter<settings[2];counter++)
                    {
                        if(textFields[counter]==((JTextField)source))
                            break;
                    }
                    if(((JTextField)source).getText().isEmpty())
                    {
                        ((JTextField)source).setText(hints[counter]);
                        ((JTextField) source).setForeground(Color.gray);
                    }
                }
            });
            textFields[counter].setBounds(100,y,500,40);
            (textFields[counter]).setFont(new Font("", Font.PLAIN,20 ));
        }


        //arrival time textfields
        JTextField []aTtextFields=new JTextField[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            aTtextFields[counter]=new JTextField("0");
            int y=(50+(counter*50));
            frame.add(aTtextFields[counter]);
            (aTtextFields[counter]).setForeground(Color.gray);
            aTtextFields[counter].addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    Object source=e.getSource();
                    if(((JTextField)source).getText().equals("0"))
                    {
                        ((JTextField)source).setText("");
                        ((JTextField)source).setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    Object source=e.getSource();
                    if(((JTextField)source).getText().isEmpty())
                    {
                        ((JTextField)source).setText("0");
                        ((JTextField)source).setForeground(Color.gray);
                    }

                }
            });
            aTtextFields[counter].setBounds(650,y,50,40);
            (aTtextFields[counter]).setFont(new Font("", Font.PLAIN,20 ));
        }


        //seconds label
        JLabel []secondLabels=new JLabel[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            secondLabels[counter]=new JLabel("sec");
            int y=(50+(counter*50));
            frame.add(secondLabels[counter]);
            secondLabels[counter].setBounds(700,y,100,40);
            (secondLabels[counter]).setFont(new Font("", Font.ITALIC,25 ));
        }



        //setting proceed button position and resizing frame
        JButton proceed=new JButton("Proceed");
        frame.add(proceed);
        proceed.setForeground(Color.RED);
        proceed.setBounds(750,proceedPosition+70,100,30);
        frame.setSize(900,proceedPosition+170);
        new Restart(frame,500,625,proceedPosition+70,proceedPosition+70,100,100,30,30);
        proceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String []jobs=new String[settings[2]];
                int []aTime=new int[settings[2]];
                for(int counter=0;counter<settings[2];counter++) {
                    jobs[counter]=textFields[counter].getText();
                    aTime[counter]=Integer.parseInt(aTtextFields[counter].getText());
                }
                frame.getContentPane().removeAll();
                frame.getContentPane().repaint();

                if(type==1){
                    (new RoundRobinScheduling(frame,settings,jobs,aTime,tq)).execute();
                }

                if(type==2){
                    (new FCFScheduling(frame,settings,jobs,aTime)).execute();
                }

                if(type==3){
                    (new SJFScheduling(frame,settings,jobs,aTime)).execute();
                }

                if(type==4){
                    (new SRTFScheduling(frame,settings,jobs,aTime)).execute();
                }


            }
        });

    }
}

class SetJobPageWithPriority {
    SetJobPageWithPriority(JFrame frame, int[] settings,int type){
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(900,600);
        int proceedPosition=0;

        String []hints=new String[10];
        hints[0]="2-3*4+5";
        hints[1]="(2-3)*(4+5)";
        hints[2]="(5+7*(6*7))+54-79";
        hints[3]="45+6-(567*6-4+56/3)";
        hints[4]="(45+56-34/2)/4*4";
        hints[5]="100-32*30/2+45*(56+345)";
        hints[6]="456*67-45/34*(45-56*67)";
        hints[7]="(45*5-(45+56-6/2*3)*2)/3*4+5";
        hints[8]="1+2-3/4*5+(1+2-3/4*5)*25/2";
        hints[9]="34-(4-5*2/4+100)*34/3";


        //Adding labels Jobs and Arrival Time
        JLabel jobs=new JLabel("Jobs");
        JLabel aT=new JLabel("Arrival Time");
        JLabel priority=new JLabel("Priority");
        frame.add(priority);
        frame.add(jobs);
        frame.add(aT);
        jobs.setBounds(100,0,100,40);
        priority.setBounds(650,0,150,40);
        aT.setBounds(800,0,150,40);
        jobs.setFont(new Font("",Font.PLAIN,25));
        aT.setFont(new Font("",Font.PLAIN,25));
        priority.setFont(new Font("",Font.PLAIN,25));



        //job numbering
        JLabel []labels=new JLabel[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            labels[counter]=new JLabel(" "+(counter+1));
            int y=(50+(counter*50));
            frame.add(labels[counter]);
            labels[counter].setBounds(50,y,100,40);
            (labels[counter]).setFont(new Font(labels[counter].getText(), Font.PLAIN,25 ));
            proceedPosition=y;
        }


        //job textfields
        JTextField []textFields=new JTextField[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            textFields[counter]=new JTextField(hints[counter]);
            int y=(50+(counter*50));
            frame.add(textFields[counter]);
            textFields[counter].setForeground(Color.gray);
            textFields[counter].addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    Object source=e.getSource();
                    int counter=0;
                    for(counter=0;counter<settings[2];counter++)
                    {
                        if(textFields[counter]==((JTextField)source))
                            break;
                    }
                    if(((JTextField)source).getText().equals(hints[counter])) {
                        ((JTextField) source).setText("");
                        ((JTextField)source).setForeground(Color.black);
                    }

                }

                @Override
                public void focusLost(FocusEvent e) {
                    Object source=e.getSource();
                    int counter=0;
                    for(counter=0;counter<settings[2];counter++)
                    {
                        if(textFields[counter]==((JTextField)source))
                            break;
                    }
                    if(((JTextField)source).getText().isEmpty())
                    {
                        ((JTextField)source).setText(hints[counter]);
                        ((JTextField) source).setForeground(Color.gray);
                    }
                }
            });
            textFields[counter].setBounds(100,y,500,40);
            (textFields[counter]).setFont(new Font("", Font.PLAIN,20 ));
        }


        //arrival time textfields
        JTextField []aTtextFields=new JTextField[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            aTtextFields[counter]=new JTextField("0");
            int y=(50+(counter*50));
            frame.add(aTtextFields[counter]);
            (aTtextFields[counter]).setForeground(Color.gray);
            aTtextFields[counter].addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    Object source=e.getSource();
                    if(((JTextField)source).getText().equals("0"))
                    {
                        ((JTextField)source).setText("");
                        ((JTextField)source).setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    Object source=e.getSource();
                    if(((JTextField)source).getText().isEmpty())
                    {
                        ((JTextField)source).setText("0");
                        ((JTextField)source).setForeground(Color.gray);
                    }

                }
            });
            aTtextFields[counter].setBounds(800,y,50,40);
            (aTtextFields[counter]).setFont(new Font("", Font.PLAIN,20 ));
        }


        //priorities textfields
        JTextField []priorities=new JTextField[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            priorities[counter]=new JTextField("1");
            int y=(50+(counter*50));
            frame.add(priorities[counter]);
            (priorities[counter]).setForeground(Color.gray);
            priorities[counter].addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    Object source=e.getSource();
                    if(((JTextField)source).getText().equals("1"))
                    {
                        ((JTextField)source).setText("");
                        ((JTextField)source).setForeground(Color.BLACK);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    Object source=e.getSource();
                    if(((JTextField)source).getText().isEmpty())
                    {
                        ((JTextField)source).setText("1");
                        ((JTextField)source).setForeground(Color.gray);
                    }

                }
            });
            priorities[counter].setBounds(650,y,50,40);
            (priorities[counter]).setFont(new Font("", Font.PLAIN,20 ));
        }

        //seconds label
        JLabel []secondLabels=new JLabel[settings[2]];
        for(int counter=0;counter<settings[2];counter++) {
            secondLabels[counter]=new JLabel("sec");
            int y=(50+(counter*50));
            frame.add(secondLabels[counter]);
            secondLabels[counter].setBounds(850,y,100,40);
            (secondLabels[counter]).setFont(new Font("", Font.ITALIC,25 ));
        }



        //setting proceed button position and resizing frame
        JButton proceed=new JButton("Proceed");
        frame.add(proceed);
        proceed.setForeground(Color.RED);
        proceed.setBounds(750,proceedPosition+70,100,30);
        frame.setSize(1000,proceedPosition+170);
        new Restart(frame,500,625,proceedPosition+70,proceedPosition+70,100,100,30,30);
        proceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String []jobs=new String[settings[2]];
                int []aTime=new int[settings[2]];
                int []priority=new int[settings[2]];
                for(int counter=0;counter<settings[2];counter++) {
                    jobs[counter]=textFields[counter].getText();
                    aTime[counter]=Integer.parseInt(aTtextFields[counter].getText());
                    priority[counter]=Integer.parseInt(priorities[counter].getText());
                }

                frame.getContentPane().removeAll();
                frame.getContentPane().repaint();
                if(type==5){
                    (new NonPreemptivePriorityScheduling(frame,settings,jobs,aTime,priority)).execute();
                }

                if(type==6){
                    (new PreemptivePriorityScheduling(frame,settings,jobs,aTime,priority)).execute();
                }
            }
        });

    }
}

class MyProcessors {
    MyProcessors(JFrame frame){

        //label choose the algorithm
        JLabel choose=new JLabel("Please choose the Scheduling algorithm");
        frame.add(choose);
        choose.setBounds(50,30,500,40);
        choose.setFont(new Font("",Font.ITALIC,25));


        //adding scheduling buttons
        JButton roundRobin=new JButton("Round Robin");
        JButton fCFS=new JButton("First Come First Served");
        JButton sJF=new JButton("Shortest Job First");
        JButton sRTF=new JButton("Shortest Remaining Time First");
        JButton nonPreemptivePrior=new JButton("Non Preemptive Priority Scheduling");
        JButton preemptivePrior=new JButton("Preemptive Priority Scheduling");
        frame.add(roundRobin);
        frame.add(fCFS);
        frame.add(sJF);
        frame.add(sRTF);
        frame.add(nonPreemptivePrior);
        frame.add(preemptivePrior);
        roundRobin.setBounds(50,100,500,40);
        roundRobin.setFont(new Font("",Font.PLAIN,25));
        fCFS.setBounds(50,150,500,40);
        fCFS.setFont(new Font("",Font.PLAIN,25));
        sJF.setBounds(50,200,500,40);
        sJF.setFont(new Font("",Font.PLAIN,25));
        sRTF.setBounds(50,250,500,40);
        sRTF.setFont(new Font("",Font.PLAIN,25));
        nonPreemptivePrior.setBounds(50,300,500,40);
        nonPreemptivePrior.setFont(new Font("",Font.PLAIN,25));
        preemptivePrior.setBounds(50,350,500,40);
        preemptivePrior.setFont(new Font("",Font.PLAIN,25));
        frame.setSize(600,450);
        //  frame.setSize(4000,4000);


        //adding actionListeners for all the Scheduling buttons
        roundRobin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                TimeQuantum.timeQuantum(frame,1);
            }
        });

        fCFS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                new StartPage(frame,2);
            }
        });

        sJF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                new StartPage(frame,3);
            }
        });

        sRTF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                new StartPage(frame,4);
            }
        });

        nonPreemptivePrior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                new StartPage(frame,5);
            }
        });

        preemptivePrior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.repaint();
                new StartPage(frame,6);
            }
        });
    }
}

class ProcessingPage{
    JButton jobButtons[],register_A,register_B,button_result,PCB_Buttons[];
    JLabel presentSituation,operatorLabel;
    ProcessingPage(JFrame frame,int []settings,String []jobs){
        LinkedList jobText_AText_BText_resultText_signalText_PCBText_operatorLabel=processingPage(frame,settings,jobs);
        jobButtons=(JButton[]) jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.get(0);
        register_A=(JButton) jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.get(1);
        register_B=(JButton)jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.get(2);
        button_result=(JButton)jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.get(3);
        presentSituation=(JLabel)jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.get(4);
        PCB_Buttons=(JButton[])jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.get(5);
        operatorLabel=(JLabel)jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.get(6);
    }
    public LinkedList processingPage(JFrame frame,int []settings,String []jobs){

        //Adding Jobs label
        JLabel joblabel=new JLabel("Jobs");
        frame.add(joblabel);
        joblabel.setBounds(50,10,100,40);
        joblabel.setFont(new Font("",Font.ITALIC,30));

        //Adding jobs in Button
        JButton []jobButtons=new JButton[settings[2]];
        for(int counter=0;counter<settings[2];counter++){
            int y=(50*(counter+1));
            jobButtons[counter]=new JButton(jobs[counter]);
            frame.add(jobButtons[counter]);
            jobButtons[counter].setFont(new Font("",Font.PLAIN,25));
            jobButtons[counter].setBackground(Color.white);
            jobButtons[counter].setBounds(50,y,400,40);
        }


        //label for job numbering
        JLabel []jobNumber=new JLabel[settings[2]];
        for (int counter=0;counter<settings[2];counter++){
            int y=(50*(counter+1));
            jobNumber[counter]=new JLabel(" "+(counter+1));
            frame.add(jobNumber[counter]);
            jobNumber[counter].setBounds(5,y,45,40);
            jobNumber[counter].setFont(new Font("",Font.ITALIC,30));
        }


        //Adding Registers A B result as Buttons and their names as label
        JButton register_A=new JButton();
        JButton register_B=new JButton();
        JButton button_result=new JButton();
        JLabel label_A=new JLabel("A");
        JLabel label_B=new JLabel("B");
        JLabel label_result=new JLabel("result");
        JLabel operator=new JLabel("+");

        frame.add(register_A);
        frame.add(register_B);
        frame.add(operator);
        frame.add(button_result);
        frame.add(label_A);
        frame.add(label_B);
        frame.add(label_result);

        register_A.setBounds(500,(((settings[2]*50)+100)/2)-50,100,40);
        label_A.setBounds(540,(((settings[2]*50)+100)/2)-75,30,25);
        register_B.setBounds(500,(((settings[2]*50)+100)/2)+50,100,40);
        label_B.setBounds(540,(((settings[2]*50)+100)/2)+90,30,25);
        button_result.setBounds(650,(((settings[2]*50)+100)/2),100,50);
        label_result.setBounds(670,(((settings[2]*50)+100)/2)+50,150,25);
        operator.setBounds(540,(((settings[2]*50)+100)/2),50,50);

        register_A.setFont(new Font("",Font.PLAIN,25));
        label_A.setFont(new Font("",Font.PLAIN,25));
        register_B.setFont(new Font("",Font.PLAIN,25));
        label_B.setFont(new Font("",Font.PLAIN,25));
        label_result.setFont(new Font("",Font.PLAIN,25));
        button_result.setFont(new Font("",Font.PLAIN,25));
        operator.setFont(new Font("",Font.PLAIN,25));

        register_A.setBackground(Color.white);
        register_B.setBackground(Color.white);
        button_result.setBackground(Color.white);



        //preparing PCBs
        JButton []PCB_Buttons=new JButton[settings[2]];
        for(int counter=0;counter<settings[2];counter++){
            PCB_Buttons[counter]=new JButton();
            frame.add(PCB_Buttons[counter]);
            PCB_Buttons[counter].setBackground(Color.WHITE);
            PCB_Buttons[counter].setFont(new Font("",Font.PLAIN,25));
            PCB_Buttons[counter].setBounds(850,(50*(counter+1)),400,40);
        }


        //PCB label
        JLabel pCB=new JLabel("Process Control Board");
        frame.add(pCB);
        pCB.setBounds(850,10,400,40);
        pCB.setFont(new Font("",Font.ITALIC,25));


        //present_situation label
        JLabel presentSituation=new JLabel();
        frame.add(presentSituation);
        presentSituation.setBounds(550,(((settings[2]*50)+100)/2)-100,300,25);
        presentSituation.setFont(new Font("",Font.ITALIC,25));
        presentSituation.setForeground(Color.RED);


        frame.setSize(4000,1000);

        //preparing returning linkedlist
        LinkedList jobText_AText_BText_resultText_signalText_PCBText_operatorLabel=new LinkedList();
        jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.add(jobButtons);
        jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.add(register_A);
        jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.add(register_B);
        jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.add(button_result);
        jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.add(presentSituation);
        jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.add(PCB_Buttons);
        jobText_AText_BText_resultText_signalText_PCBText_operatorLabel.add(operator);
        return jobText_AText_BText_resultText_signalText_PCBText_operatorLabel;
    }
}


class RoundRobinScheduling extends SwingWorker{
    private JFrame frame;private int []settings;private String []jobs;
    private int []aTime;private int time=0;private int tQ;
    private LinkedList<Stack> postfixes=new LinkedList<>();
    RoundRobinScheduling(JFrame frame,int []settings,String []jobs,int []aTime,int tQ){
        this.frame=frame;this.settings=settings;this.jobs=jobs;this.aTime=aTime;
        this.tQ=tQ;
        for(int counter=0;counter<jobs.length;counter++) {
            postfixes.addLast(StringHandler.postfix(jobs[counter]));
        }


    }

    @Override
    protected Object doInBackground() throws Exception {
        //to get the fcfs processing order
        LinkedList<Integer> fCFSProcessingOrder=fCFSProcessingOrder(aTime);

        //for adding time to the frame (Constantly Changing)
        RoundRobinScheduling.Time tame=new RoundRobinScheduling.Time(frame);
        Thread thread=new Thread(tame);

        //adding a new processing page
        ProcessingPage processingPage=new ProcessingPage(frame,settings,jobs);
        int []burstTime=StringHandler.burstTime(jobs,settings[2]);

        thread.start();

        //ListIterator<Integer> iterator=fCFSProcessingOrder.listIterator();
        int count=0;
        while (count<fCFSProcessingOrder.size()){
            int temp=fCFSProcessingOrder.get(count);
            count++;
            temp--;
            int counter=tQ;
            if(aTime[temp]>time){
                processingPage.presentSituation.setText("Idle Condition");
                TimeUnit.SECONDS.sleep(aTime[temp]-time);
            }


            processingPage.presentSituation.setText("Context Switching");
            TimeUnit.SECONDS.sleep(settings[0]);




            while (counter!=0) {
                counter--;
                if(burstTime[temp]==0){
                    processingPage.jobButtons[temp].setBackground(Color.lightGray);
                    break;
                }

                processingPage.jobButtons[temp].setBackground(Color.RED);
                processingPage.presentSituation.setText("processing");
                TimeUnit.SECONDS.sleep(settings[1]);


                LinkedList singleOperation = StringHandler.singleOperation(postfixes.get(temp));
                processingPage.register_A.setText((String) singleOperation.get(0));
                processingPage.operatorLabel.setText((String) singleOperation.get(1));
                processingPage.register_B.setText((String) singleOperation.get(2));
                processingPage.button_result.setText((String) singleOperation.get(3));
                postfixes.remove(temp);
                postfixes.add(temp, (Stack) singleOperation.get(4));
                processingPage.PCB_Buttons[temp].setText(StringHandler.stackToString(postfixes.get(temp)));
                processingPage.jobButtons[temp].setBackground(Color.WHITE);
                time++;
                burstTime[temp]--;
            }
            if(burstTime[temp]!=0){
                fCFSProcessingOrder.addLast(temp+1);
            }
            else
                processingPage.jobButtons[temp].setBackground(Color.lightGray);

        }
        TimeUnit.SECONDS.sleep(2);
        new Restart(frame);

        return null;
    }



    private LinkedList<Integer> fCFSProcessingOrder(int[] aTime){
        LinkedList<Integer> processingOrder=new LinkedList<Integer>();
        ArrayList<Integer> aTimeCopy=new ArrayList<Integer>();
        for(int i=0;i<aTime.length;i++)
            aTimeCopy.add(aTime[i]);
        Collections.sort(aTimeCopy);
        for(int i=0;i<aTime.length;i++) {
            for(int j=0;j<aTime.length;j++) {
                if(aTimeCopy.get(i)==Integer.valueOf(aTime[j])) {
                    processingOrder.add(j + 1);
                    aTime[j] = -1;
                }
            }
        }
        return processingOrder;
    }

    class Time implements Runnable{
        JFrame frame;
        Time(JFrame frame){
            this.frame=frame;
        }
        @Override
        public void run() {
            JLabel clock=new JLabel("0");
            frame.add(clock);
            clock.setBounds(0,0,10,10);
            while(true)
                clock.setText(Integer.toString(time));

        }
    }

}

class TimeQuantum{
    static void timeQuantum(JFrame frame,int type){
        JComboBox tQ;
        final int[] tq = {0};
        JLabel chooseTimeQuantum=new JLabel("Choose the Time Quantum");
        frame.add(chooseTimeQuantum);
        chooseTimeQuantum.setBounds(50,30,500,50);
        chooseTimeQuantum.setFont(new Font("",Font.ITALIC,30));

        String[] nos = {" 1 Second"," 2 Seconds"," 3 Seconds"," 4 Seconds"," 5 Seconds"," 6 Seconds"," 7 Seconds"," 8 Seconds"," 9 Seconds"," 10 Seconds"};
        tQ=new JComboBox(nos);
        frame.add(tQ);
        tQ.setBounds(500,40,150,30);


        JButton proceed=new JButton("Proceed");
        frame.add(proceed);
        proceed.setBounds(550,150,200,40);
        new Restart(frame,50,300,150,150,200,200,40,40);
        proceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tq[0] =tQ.getSelectedIndex()+1;
                frame.getContentPane().removeAll();
                frame.repaint();
                new StartPage(frame,type, tq[0]);

            }
        });

        frame.setSize(800,300);
    }
}



class FCFScheduling extends SwingWorker{
    private JFrame frame;private int []settings;private String []jobs;
    private int []aTime;private int time=0;
    protected Object doInBackground() throws Exception {
        Time tame=new Time(frame);
        Thread thread=new Thread(tame);


        ProcessingPage processingPage=new ProcessingPage(frame,settings,jobs);
        int []burstTime=StringHandler.burstTime(jobs,settings[2]);
        LinkedList<Integer> processingOrder=processingOrder(aTime.clone());


        int []completionTime=new int[settings[2]];
        thread.start();
        for(int counter=0;counter<settings[2];counter++){
            if((aTime[processingOrder.get(counter)-1])>=time) {
                processingPage.presentSituation.setText("Idle State");
                try {
                    SECONDS.sleep(aTime[processingOrder.get(counter)-1]-time);
                } catch (InterruptedException e) {
                    processingPage.presentSituation.setText(e.getMessage());
                }
                time=time+(aTime[processingOrder.get(counter)-1]-time);
            }
            processingPage.presentSituation.setText("Context Switching");
            try {
                SECONDS.sleep(settings[0]);
            } catch (InterruptedException e) {
                processingPage.presentSituation.setText(e.getMessage());
            }
            time = time + settings[0];


            if(aTime[processingOrder.get(counter)-1]<=time) {
                processingPage.jobButtons[processingOrder.get(counter)-1].setBackground(Color.RED);
                time = time + processJob(jobs[processingOrder.get(counter)-1], aTime[processingOrder.get(counter)-1], settings[1], processingPage, processingOrder.get(counter)-1, burstTime[processingOrder.get(counter)-1]);
                processingPage.jobButtons[processingOrder.get(counter)-1].setBackground(Color.LIGHT_GRAY);
                completionTime[counter]=time;
            }

        }

        TimeUnit.SECONDS.sleep(2);
        new Restart(frame);
        return null;
    }
    FCFScheduling(JFrame frame,int []settings,String []jobs,int []aTime){
        this.frame=frame;this.aTime=aTime;this.settings=settings;
        this.jobs=jobs;
    }
    private int processJob(String job,int aTime,int cycleTime,ProcessingPage processingPage,int jobNumber,int burstTime){
        Stack<String> postfix=StringHandler.postfix(job);
        int temp=burstTime;
        int time=0;
        while(temp>0){
            temp--;
            LinkedList<String> singleOperation= StringHandler.singleOperation(postfix);
            if(!singleOperation.get(0).equals("0.0")){
                processingPage.PCB_Buttons[jobNumber].setText(StringHandler.stackToString(postfix));
                processingPage.register_A.setText(singleOperation.get(0));
                processingPage.register_B.setText(singleOperation.get(2));
                processingPage.presentSituation.setText("processing");
                processingPage.operatorLabel.setText(singleOperation.get(1));
                processingPage.button_result.setText(singleOperation.get(3));
                try {
                    SECONDS.sleep(cycleTime);
                }
                catch (InterruptedException e){
                    processingPage.presentSituation.setText(e.getMessage());
                }
                time=time+cycleTime;
            }
            else
                break;
        }
        processingPage.PCB_Buttons[jobNumber].setText("");
        processingPage.jobButtons[jobNumber].setText(processingPage.jobButtons[jobNumber].getText()+" ="+postfix.get(0));
        return time;
    }
    private LinkedList<Integer> processingOrder(int[] aTime){
        LinkedList<Integer> processingOrder=new LinkedList<Integer>();
        ArrayList<Integer> aTimeCopy=new ArrayList<Integer>();
        for(int i=0;i<aTime.length;i++)
            aTimeCopy.add(aTime[i]);
        Collections.sort(aTimeCopy);
        for(int i=0;i<aTime.length;i++) {
            for(int j=0;j<aTime.length;j++) {
                if(aTimeCopy.get(i)==Integer.valueOf(aTime[j])) {
                    processingOrder.add(j + 1);
                    aTime[j] = -1;
                }
            }
        }
        return processingOrder;
    }

    class Time implements Runnable{
        JFrame frame;
        Time(JFrame frame){
            this.frame=frame;
        }
        @Override
        public void run() {
            JLabel clock=new JLabel("0");
            frame.add(clock);
            clock.setBounds(0,0,10,10);
            while(true)
                clock.setText(Integer.toString(time));

        }
    }

}



class SJFScheduling extends SwingWorker<Void,Void>{
    private JFrame frame;private int []settings;private String []jobs;
    private int []aTime;private int time=0;
    SJFScheduling(JFrame frame,int []settings,String []jobs,int []aTime){
        this.frame=frame;this.settings=settings;this.jobs=jobs;this.aTime=aTime;
    }

    @Override
    protected Void doInBackground() throws Exception {

        //for adding time to the frame (Constantly Changing)
        Time tame=new Time(frame);
        Thread thread=new Thread(tame);

        //adding a new processing page
        ProcessingPage processingPage=new ProcessingPage(frame,settings,jobs);
        int []burstTime=StringHandler.burstTime(jobs,settings[2]);
        //to get the processing order according to the burst time
        LinkedList<Integer> processingOrder=processingOrder(burstTime.clone(),aTime.clone());
        int []completionTime=new int[settings[2]];
        //starting the time thread
        thread.start();
        // this for loop is to process all the jobs according to the processing order
        for(int counter=0;counter<settings[2];counter++){
            //to processor in idle state if arrival time of the  job to processed is greater than the current time
            if((aTime[processingOrder.get(counter)-1])>=time) {
                processingPage.presentSituation.setText("Idle State");
                try {
                    SECONDS.sleep(aTime[processingOrder.get(counter)-1]-time);
                } catch (InterruptedException e) {
                    processingPage.presentSituation.setText(e.getMessage());
                }
                time=time+(aTime[processingOrder.get(counter)-1]-time);
            }
            //for context switching
            processingPage.presentSituation.setText("Context Switching");
            try {
                SECONDS.sleep(settings[0]);
            } catch (InterruptedException e) {
                processingPage.presentSituation.setText(e.getMessage());
            }
            time = time + settings[0];
            //to process a particular job
            if(aTime[processingOrder.get(counter)-1]<=time) {
                processingPage.jobButtons[processingOrder.get(counter)-1].setBackground(Color.RED);
                time = time + processJob(jobs[processingOrder.get(counter)-1], aTime[processingOrder.get(counter)-1], settings[1], processingPage, processingOrder.get(counter)-1, burstTime[processingOrder.get(counter)-1]);
                processingPage.jobButtons[processingOrder.get(counter)-1].setBackground(Color.LIGHT_GRAY);
                completionTime[processingOrder.get(counter)-1]=time;
            }
        }

        TimeUnit.SECONDS.sleep(2);
        new Restart(frame);

        return null;
    }

    private static LinkedList<Integer> processingOrder(int []burstTime, int []aTime){
        LinkedList<Integer> processingOrderWithTime=new LinkedList<Integer>();
        int completedJobs=0;
        LinkedList<Integer> burstTimeCopy=new LinkedList<Integer>();
        for(int i=0;i<burstTime.length;i++)
            burstTimeCopy.addLast(burstTime[i]);
        Collections.sort(burstTimeCopy);
        for(int time=0;completedJobs!=burstTime.length;time++) {
            int me=0;
            completedJobs=0;
            burstTimeCopy.removeAll(burstTimeCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (aTime[i] <= time) {
                    burstTimeCopy.addLast(burstTime[i]);
                    me++;
                }
            }
            Collections.sort(burstTimeCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (burstTime[i] == 0) {
                    completedJobs++;
                }
            }
            for (int aBurstTime=0;aBurstTime<me; aBurstTime++) {
                int added=0;
                for (int counter=0;counter<burstTime.length&&!(burstTimeCopy.get(aBurstTime).equals(new Integer(0)));counter++) {
                    if (((burstTimeCopy.get(aBurstTime)).equals(burstTime[counter]))&&(burstTime[counter]!=0)&&(aTime[counter]<=time)) {
                        processingOrderWithTime.addLast(counter+1);
                        int temp = burstTimeCopy.remove(aBurstTime).intValue();
                        burstTimeCopy.add(aBurstTime, 0);
                        time=time+burstTime[counter];
                        burstTime[counter]=0;

                        added++;
                        break;
                    }
                }
                if(added==1)
                    break;


            }
        }
        return processingOrderWithTime;
    }


    private int processJob(String job,int aTime,int cycleTime,ProcessingPage processingPage,int jobNumber,int burstTime){
        Stack<String> postfix=StringHandler.postfix(job);
        int temp=burstTime;
        while(temp>0){
            temp--;
            LinkedList<String> singleOperation= StringHandler.singleOperation(postfix);
            if(!singleOperation.get(0).equals("0.0")){
                processingPage.PCB_Buttons[jobNumber].setText(StringHandler.stackToString(postfix));
                processingPage.register_A.setText(singleOperation.get(0));
                processingPage.register_B.setText(singleOperation.get(2));
                processingPage.presentSituation.setText("processing");
                processingPage.operatorLabel.setText(singleOperation.get(1));
                processingPage.button_result.setText(singleOperation.get(3));
                try {
                    SECONDS.sleep(cycleTime);
                }
                catch (InterruptedException e){
                    processingPage.presentSituation.setText(e.getMessage());
                }
                time=time+cycleTime;
            }
            else
                break;
        }
        processingPage.PCB_Buttons[jobNumber].setText("");
        processingPage.jobButtons[jobNumber].setText(processingPage.jobButtons[jobNumber].getText()+" ="+postfix.get(0));
        return time;
    }

    class Time implements Runnable{
        JFrame frame;
        Time(JFrame frame){
            this.frame=frame;
        }
        @Override
        public void run() {
            JLabel clock=new JLabel("0");
            frame.add(clock);
            clock.setBounds(0,0,10,10);
            while(true)
                clock.setText(Integer.toString(time));

        }
    }
}


class SRTFScheduling extends SwingWorker<Void ,Void>{
    private JFrame frame;private int []settings;private String []jobs;
    private int []aTime;private int time=0;
    private LinkedList<Stack> postfixes=new LinkedList<>();
    SRTFScheduling(JFrame frame,int []settings,String []jobs,int []aTime){
        this.frame=frame;this.settings=settings;this.jobs=jobs;this.aTime=aTime;
        for(int counter=0;counter<jobs.length;counter++){
            postfixes.addLast(StringHandler.postfix(jobs[counter]));
        }
    }


    @Override
    protected Void doInBackground() throws Exception {
        //for adding time to the frame (Constantly Changing)
        SRTFScheduling.Time tame=new SRTFScheduling.Time(frame);
        Thread thread=new Thread(tame);

        //adding a new processing page
        ProcessingPage processingPage=new ProcessingPage(frame,settings,jobs);
        int []burstTime=StringHandler.burstTime(jobs,settings[2]);
        //to get the processing order according to the burst time
        LinkedList<Integer> processingOrder=processingOrder(burstTime.clone(),aTime.clone());
        int []completionTime=new int[settings[2]];
        //starting the time thread
        thread.start();
        ListIterator<Integer> iterator=processingOrder.listIterator();
        int i=-1;
        while (iterator.hasNext()){
            i++;
            int temp=iterator.next();
            if(aTime[temp]>time){
                processingPage.presentSituation.setText("Idle Condition");
                TimeUnit.SECONDS.sleep(aTime[temp]-time);
            }

            if(i==0){
                processingPage.presentSituation.setText("Context Switching");
                TimeUnit.SECONDS.sleep(settings[0]);
            }
            else if(!(processingOrder.get(i-1).equals(processingOrder.get(i)))) {
                processingPage.presentSituation.setText("Context Switching");
                TimeUnit.SECONDS.sleep(settings[0]);
            }

            processingPage.jobButtons[temp].setBackground(Color.RED);
            processingPage.presentSituation.setText("processing");
            TimeUnit.SECONDS.sleep(settings[1]);


            LinkedList singleOperation=StringHandler.singleOperation(postfixes.get(temp));
            processingPage.register_A.setText((String) singleOperation.get(0));
            processingPage.operatorLabel.setText((String)singleOperation.get(1));
            processingPage.register_B.setText((String)singleOperation.get(2));
            processingPage.button_result.setText((String)singleOperation.get(3));
            postfixes.remove(temp);
            postfixes.add(temp,(Stack)singleOperation.get(4));
            processingPage.PCB_Buttons[temp].setText(StringHandler.stackToString(postfixes.get(temp)));
            processingPage.jobButtons[temp].setBackground(Color.lightGray);
            time++;
        }

        TimeUnit.SECONDS.sleep(2);
        new Restart(frame);
        return null;
    }


    private static LinkedList<Integer> processingOrder(int []burstTime, int []aTime){
        LinkedList<Integer> processingOrderWithTime=new LinkedList<Integer>();
        int completedJobs=0;
        LinkedList<Integer> burstTimeCopy=new LinkedList<Integer>();
        for(int i=0;i<burstTime.length;i++)
            burstTimeCopy.addLast(burstTime[i]);
        Collections.sort(burstTimeCopy);
        for(int time=0;completedJobs!=burstTime.length;time++) {
            int me=0;
            completedJobs=0;
            burstTimeCopy.removeAll(burstTimeCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (aTime[i] <= time) {
                    burstTimeCopy.addLast(burstTime[i]);
                    me++;
                }
            }
            Collections.sort(burstTimeCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (burstTime[i] == 0) {
                    completedJobs++;
                }
            }
            for (int aBurstTime=0;aBurstTime<me; aBurstTime++) {
                int added=0;
                for (int counter=0;counter<burstTime.length&&!(burstTimeCopy.get(aBurstTime).equals(new Integer(0)));counter++) {
                    if (((burstTimeCopy.get(aBurstTime)).equals(burstTime[counter]))&&(burstTime[counter]!=0)&&(aTime[counter]<=time)) {
                        processingOrderWithTime.addLast(counter);
                        int temp = burstTimeCopy.remove(aBurstTime).intValue();
                        burstTimeCopy.add(aBurstTime, temp - 1);
                        burstTime[counter]--;
                        added++;
                        break;
                    }
                }
                if(added==1)
                    break;


            }
        }
        return processingOrderWithTime;
    }


    class Time implements Runnable{
        JFrame frame;
        Time(JFrame frame){
            this.frame=frame;
        }
        @Override
        public void run() {
            JLabel clock=new JLabel("0");
            frame.add(clock);
            clock.setBounds(0,0,10,10);
            while(true)
                clock.setText(Integer.toString(time));

        }
    }
}


class NonPreemptivePriorityScheduling extends SwingWorker<Void,Void>{
    private JFrame frame;private int []settings;private String []jobs;
    private int []aTime;private int time=0;private int []priority;
    NonPreemptivePriorityScheduling(JFrame frame,int []settings,String []jobs,int []aTime,int []priority){
        this.frame=frame;this.settings=settings;this.jobs=jobs;this.aTime=aTime;
        this.priority=priority;
    }

    @Override
    protected Void doInBackground() throws Exception {

        //for adding time to the frame (Constantly Changing)
        Time tame=new Time(frame);
        Thread thread=new Thread(tame);

        //adding a new processing page
        ProcessingPage processingPage=new ProcessingPage(frame,settings,jobs);
        int []burstTime=StringHandler.burstTime(jobs,settings[2]);
        //to get the processing order according to the burst time
        LinkedList<Integer> processingOrder=processingOrder(priority.clone(),aTime.clone());
        int []completionTime=new int[settings[2]];
        //starting the time thread
        thread.start();
        // this for loop is to process all the jobs according to the processing order
        for(int counter=0;counter<settings[2];counter++){
            //to processor in idle state if arrival time of the  job to processed is greater than the current time
            if((aTime[processingOrder.get(counter)-1])>=time) {
                processingPage.presentSituation.setText("Idle State");
                try {
                    SECONDS.sleep(aTime[processingOrder.get(counter)-1]-time);
                } catch (InterruptedException e) {
                    processingPage.presentSituation.setText(e.getMessage());
                }
                time=time+(aTime[processingOrder.get(counter)-1]-time);
            }
            //for context switching
            processingPage.presentSituation.setText("Context Switching");
            try {
                SECONDS.sleep(settings[0]);
            } catch (InterruptedException e) {
                processingPage.presentSituation.setText(e.getMessage());
            }
            time = time + settings[0];
            //to process a particular job
            if(aTime[processingOrder.get(counter)-1]<=time) {
                processingPage.jobButtons[processingOrder.get(counter)-1].setBackground(Color.RED);
                time = time + processJob(jobs[processingOrder.get(counter)-1], aTime[processingOrder.get(counter)-1], settings[1], processingPage, processingOrder.get(counter)-1, burstTime[processingOrder.get(counter)-1]);
                processingPage.jobButtons[processingOrder.get(counter)-1].setBackground(Color.LIGHT_GRAY);
                completionTime[processingOrder.get(counter)-1]=time;
            }
        }

        TimeUnit.SECONDS.sleep(2);
        new Restart(frame);
        return null;
    }

    private static LinkedList<Integer> processingOrder(int []burstTime, int []aTime){
        LinkedList<Integer> processingOrderWithTime=new LinkedList<Integer>();
        int completedJobs=0;
        LinkedList<Integer> burstTimeCopy=new LinkedList<Integer>();
        for(int i=0;i<burstTime.length;i++)
            burstTimeCopy.addLast(burstTime[i]);
        Collections.sort(burstTimeCopy);
        for(int time=0;completedJobs!=burstTime.length;time++) {
            int me=0;
            completedJobs=0;
            burstTimeCopy.removeAll(burstTimeCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (aTime[i] <= time) {
                    burstTimeCopy.addLast(burstTime[i]);
                    me++;
                }
            }
            Collections.sort(burstTimeCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (burstTime[i] == 0) {
                    completedJobs++;
                }
            }
            for (int aBurstTime=0;aBurstTime<me; aBurstTime++) {
                int added=0;
                for (int counter=0;counter<burstTime.length&&!(burstTimeCopy.get(aBurstTime).equals(new Integer(0)));counter++) {
                    if (((burstTimeCopy.get(aBurstTime)).equals(burstTime[counter]))&&(burstTime[counter]!=0)&&(aTime[counter]<=time)) {
                        processingOrderWithTime.addLast(counter+1);
                        int temp = burstTimeCopy.remove(aBurstTime).intValue();
                        burstTimeCopy.add(aBurstTime, 0);
                        time=time+burstTime[counter];
                        burstTime[counter]=0;

                        added++;
                        break;
                    }
                }
                if(added==1)
                    break;


            }
        }
        return processingOrderWithTime;
    }


    private int processJob(String job,int aTime,int cycleTime,ProcessingPage processingPage,int jobNumber,int burstTime){
        Stack<String> postfix=StringHandler.postfix(job);
        int temp=burstTime;
        while(temp>0){
            temp--;
            LinkedList<String> singleOperation= StringHandler.singleOperation(postfix);
            if(!singleOperation.get(0).equals("0.0")){
                processingPage.PCB_Buttons[jobNumber].setText(StringHandler.stackToString(postfix));
                processingPage.register_A.setText(singleOperation.get(0));
                processingPage.register_B.setText(singleOperation.get(2));
                processingPage.presentSituation.setText("processing");
                processingPage.operatorLabel.setText(singleOperation.get(1));
                processingPage.button_result.setText(singleOperation.get(3));
                try {
                    SECONDS.sleep(cycleTime);
                }
                catch (InterruptedException e){
                    processingPage.presentSituation.setText(e.getMessage());
                }
                time=time+cycleTime;
            }
            else
                break;
        }
        processingPage.PCB_Buttons[jobNumber].setText("");
        processingPage.jobButtons[jobNumber].setText(processingPage.jobButtons[jobNumber].getText()+" ="+postfix.get(0));
        return time;
    }

    class Time implements Runnable{
        JFrame frame;
        Time(JFrame frame){
            this.frame=frame;
        }
        @Override
        public void run() {
            JLabel clock=new JLabel("0");
            frame.add(clock);
            clock.setBounds(0,0,10,10);
            while(true)
                clock.setText(Integer.toString(time));

        }
    }
}


class PreemptivePriorityScheduling extends SwingWorker<Void ,Void > {
    private JFrame frame;private int []settings;private String []jobs;
    private int []aTime;private int time=0;private int []priority;
    private LinkedList<Stack> postfixes=new LinkedList<>();
    PreemptivePriorityScheduling(JFrame frame,int []settings,String []jobs,int []aTime,int []priority){
        this.frame=frame;this.settings=settings;this.jobs=jobs;this.aTime=aTime;
        for(int counter=0;counter<jobs.length;counter++){
            postfixes.addLast(StringHandler.postfix(jobs[counter]));
        }
        this.priority=priority;
    }


    @Override
    protected Void doInBackground() throws Exception {
        //for adding time to the frame (Constantly Changing)
        PreemptivePriorityScheduling.Time tame=new PreemptivePriorityScheduling.Time(frame);
        Thread thread=new Thread(tame);

        //adding a new processing page
        ProcessingPage processingPage=new ProcessingPage(frame,settings,jobs);
        int []burstTime=StringHandler.burstTime(jobs,settings[2]);
        //to get the processing order according to the burst time
        LinkedList<Integer> processingOrder=processingOrder(priority.clone(),burstTime.clone(),aTime.clone());
        int []completionTime=new int[settings[2]];
        //starting the time thread
        thread.start();
        ListIterator<Integer> iterator=processingOrder.listIterator();
        int i=-1;
        while (iterator.hasNext()){
            i++;
            int temp=iterator.next();
            if(aTime[temp]>time){
                processingPage.presentSituation.setText("Idle Condition");
                TimeUnit.SECONDS.sleep(aTime[temp]-time);
            }

            if(i==0){
                processingPage.presentSituation.setText("Context Switching");
                TimeUnit.SECONDS.sleep(settings[0]);
            }
            else if(!(processingOrder.get(i-1).equals(processingOrder.get(i)))) {
                processingPage.presentSituation.setText("Context Switching");
                TimeUnit.SECONDS.sleep(settings[0]);
            }

            processingPage.jobButtons[temp].setBackground(Color.RED);
            processingPage.presentSituation.setText("processing");
            TimeUnit.SECONDS.sleep(settings[1]);


            LinkedList singleOperation=StringHandler.singleOperation(postfixes.get(temp));
            processingPage.register_A.setText((String) singleOperation.get(0));
            processingPage.operatorLabel.setText((String)singleOperation.get(1));
            processingPage.register_B.setText((String)singleOperation.get(2));
            processingPage.button_result.setText((String)singleOperation.get(3));
            postfixes.remove(temp);
            postfixes.add(temp,(Stack)singleOperation.get(4));
            processingPage.PCB_Buttons[temp].setText(StringHandler.stackToString(postfixes.get(temp)));
            processingPage.jobButtons[temp].setBackground(Color.lightGray);
            time++;
        }


        TimeUnit.SECONDS.sleep(2);
        new Restart(frame);
        return null;
    }


    private static LinkedList<Integer> processingOrder(int[] priority,int []burstTime, int []aTime){
        LinkedList<Integer> processingOrderWithTime=new LinkedList<Integer>();
        int completedJobs=0;
        LinkedList<Integer> burstTimeCopy=new LinkedList<Integer>();
        LinkedList<Integer> priorityCopy=new LinkedList<Integer>();
        for(int i=0;i<burstTime.length;i++)
            burstTimeCopy.addLast(burstTime[i]);
        for(int i=0;i<burstTime.length;i++)
            priorityCopy.addLast(priority[i]);
        Collections.sort(burstTimeCopy);
        for(int time=0;completedJobs!=burstTime.length;time++) {
            int me=0;
            completedJobs=0;
            burstTimeCopy.removeAll(burstTimeCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (aTime[i] <= time) {
                    burstTimeCopy.addLast(burstTime[i]);
                    me++;
                }
            }
            Collections.sort(burstTimeCopy);
            Collections.sort(priorityCopy);
            for(int i=0;i<burstTime.length;i++) {
                if (burstTime[i] == 0) {
                    priority[i]=0;
                    completedJobs++;
                }
            }
            for (int aBurstTime=0;aBurstTime<me; aBurstTime++) {
                int added=0;
                for (int counter=0;counter<burstTime.length&&!(burstTimeCopy.get(aBurstTime).equals(new Integer(0)));counter++) {
                    if (((priorityCopy.get(aBurstTime)).equals(priority[counter]))&&(priority[counter]!=0)&&(aTime[counter]<=time)) {
                        processingOrderWithTime.addLast(counter);
                        int temp = burstTimeCopy.remove(aBurstTime).intValue();
                        burstTimeCopy.add(aBurstTime, temp - 1);
                        burstTime[counter]--;
                        added++;
                        break;
                    }
                }
                if(added==1)
                    break;


            }
        }
        return processingOrderWithTime;
    }


    class Time implements Runnable{
        JFrame frame;
        Time(JFrame frame){
            this.frame=frame;
        }
        @Override
        public void run() {
            JLabel clock=new JLabel("0");
            frame.add(clock);
            clock.setBounds(0,0,10,10);
            while(true)
                clock.setText(Integer.toString(time));

        }
    }
}


class Restart {
    private int x1,x2,y1,y2,width1,width2,height1,height2;
    Restart(JFrame frame){
        x1=130;x2=450;y1=180;y2=180;width1=150;width2=150;height1=50;height2=50;
        restart(frame);
    }
    Restart(JFrame frame,int x1,int x2,int y1,int y2,int width1,int width2,int height1,int height2 ) {
        this.x1=x1;this.x2=x2;
        this.y1=y1;this.y2=y2;
        this.width1=width1;this.width2=width2;
        this.height1=height1;this.height2=height2;
        JButton restart = new JButton("Restart");
        JButton close = new JButton("Close");
        frame.add(restart);
        frame.add(close);
        restart.setBounds(x2,y2,width2,height2);
        close.setBounds(x1,y1,width1,height1);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (frame.getContentPane()).removeAll();
                frame.repaint();
                (new FRame()).fRame(frame);
            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Close.close(frame);
            }
        });

    }
    public void restart(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.setSize(800, 500);
        JButton restart = new JButton("Restart");
        JButton close = new JButton("Close");
        frame.add(restart);
        frame.add(close);
        restart.setBounds(x2,y2,width2,height2);
        close.setBounds(x1,y1,width1,height1);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (frame.getContentPane()).removeAll();
                frame.repaint();
                (new FRame()).fRame(frame);
            }
        });
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Close.close(frame);
            }
        });

    }
}
class Close  {
    public static void close(JFrame frame){
        WindowEvent win=new WindowEvent(frame,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(win);

    }
}