import java.util.LinkedList;
import java.util.Queue;

/**
 * Created on 19.03.2017.
 */
class FLFCopy {

    String word;
    Boolean nextCharFlag;
    Boolean errorFlag;
    Integer sizeOfString;
    Integer levelOfPriority;
    Boolean lastSymbol;
    Boolean eMessage;
    String line;
    FLFPart rootOfTree;

    Queue<FLFPart> inputQueue;

    public FLFCopy() {
        word = "";
        nextCharFlag = false;
        errorFlag = false;
        sizeOfString = 0;
        lastSymbol = false;
        eMessage = false;
        line = "";
        inputQueue = new LinkedList<FLFPart>();
    }

    public void printQueue(Queue<FLFPart> iQ) {

        Queue<FLFPart> inputQueue = new LinkedList<FLFPart>(iQ);

        System.out.println();
        System.out.print("Start print queue");
        System.out.println();
        System.out.print("Queue:");
        System.out.println();
        while (!inputQueue.isEmpty()){
            System.out.print(inputQueue.peek());
            FLFPart t = inputQueue.peek();
            System.out.print(t.typeTreePart);
            if(!t.typeTreePart){
                System.out.print(" " + t.operatorText + " priority " + t.priority);
                System.out.println();
            }else{
                System.out.print(" " + t.symbolsText);
                System.out.println();
            }
            inputQueue.poll();
        }
        System.out.print("End print queue");
        System.out.println();
        System.out.println();
    }

    void printTree(FLFPart rootOfTree, int level){
        for(int i = 0; i < level; i++){
            System.out.print("->");
        }
        if(rootOfTree.typeTreePart){
            System.out.print(rootOfTree.symbolsText);
            System.out.println();
            return;
        }
        System.out.print(rootOfTree.operatorText);
        System.out.println();
        if(rootOfTree.typeChild){
            if(rootOfTree.singleChild != null){
                printTree(rootOfTree.singleChild, level+1);
            }

        }else{
            if(rootOfTree.leftChild != null){
                printTree(rootOfTree.leftChild, level+1);
            }
            if(rootOfTree.rightChild != null){
                printTree(rootOfTree.rightChild, level+1);
            }
        }
    }

    void createOperand(Integer prior, String word){
        FLFPart newTreePart = new FLFPart();
        newTreePart.priority = prior + levelOfPriority;

        if(word.equals("*")){
            newTreePart.typeChild = true;
        }else{
            newTreePart.typeChild = false;
        }

        newTreePart.typeTreePart = false;
        newTreePart.operatorText = word;
        inputQueue.add(newTreePart);
        lastSymbol = false;
    }

    void createSymbol(String word, Queue<FLFPart> inputQueue){
        FLFPart newTreePart = new FLFPart();
        newTreePart.symbolsText = word;
        newTreePart.typeTreePart = true;
        inputQueue.add(newTreePart);
        lastSymbol = true;
    }


    Queue<FLFPart> createQueue(String line){
        this.line = line;
        Queue<FLFPart> empty = new LinkedList<FLFPart>();
        word = "";
        levelOfPriority = 0;
        errorFlag = false;
        lastSymbol = false;
        eMessage = false;
        sizeOfString = line.length();

        for(int i = 0; i < sizeOfString; i++){

            if(nextCharFlag){
                nextCharFlag = false;
                continue;
            }
            if((line.charAt(i) >= 'a' && line.charAt(i) <='z')||(line.charAt(i) >= 'A' && line.charAt(i) <='Z')||(line.charAt(i) >= '0' && line.charAt(i) <='9')){
                word += line.charAt(i);
                continue;
            }
            if(line.charAt(i) == ' '){
                continue;
            }
            switch(line.charAt(i)){
                case '*':
//                    if(word.length() == 0){
//                        errorFlag = true;
//                        break;
//                    }
//                    createSymbol(word, inputQueue);
                    word = "";
                    createOperand(4, "*");
                    break;
                case '|':
                    if(word.length() == 0 && line.charAt(i-1) != '*'){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word, inputQueue);
                    word = "";
                    createOperand(2, "|");
                    break;
                case '&':
                    if(word.length() == 0 && line.charAt(i-1) != '*'){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word, inputQueue);
                    word = "";
                    createOperand(3, "&");
                    break;
                case '(':
                    if(i>0){
                        if((line.charAt(i-1) >= 'a' && line.charAt(i-1) <='z')||(line.charAt(i-1) >= 'A' && line.charAt(i-1) <='Z')||(line.charAt(i-1) >= '0' && line.charAt(i-1) <='9')){
                            errorFlag = true;
                            break;
                        }
                    }
                    levelOfPriority += 10;
                    break;
                case ')':

                    if(i!=line.length()-1){
                        System.out.println(line.charAt(i));
                        if((line.charAt(i+1) >= 'a' && line.charAt(i+1) <='z')||(line.charAt(i+1) >= 'A' && line.charAt(i+1) <='Z')||(line.charAt(i+1) >= '0' && line.charAt(i+1) <='9')){

                            errorFlag = true;
                            break;
                        }
                    }
                    levelOfPriority -= 10;
                    break;
                default:
                    errorFlag = true;
                    break;
            }
            if(errorFlag){

                System.out.print("---------------------------");
                System.out.println();
                System.out.print("input line: ");
                System.out.println();


                System.out.print("error input line: " + line);
                System.out.println();

                eMessage = true;
                break;
            }
        }

        if(levelOfPriority != 0 && !errorFlag){
            System.out.print("Wrong Bracket");
            System.out.println();

            errorFlag = true;
        }

        if(word.length() > 0){
            createSymbol(word, inputQueue);
            word = "";
        }
        if(!lastSymbol && !eMessage){
            errorFlag = true;
            System.out.print("---------------------------");
            System.out.println();
            System.out.print("input line: " + line);
            System.out.println();

            System.out.print("error input line: " + line);
            System.out.println();

        }
        if(errorFlag){
            return new LinkedList<FLFPart>();
        }
        System.out.print(inputQueue);
        return inputQueue;

    }




    FLFPart createTree(Queue<FLFPart> inputQueue){
        FLFPart rootOfTree = new FLFPart();
        FLFPart currentPlace = new FLFPart();
        FLFPart tempPart = new FLFPart();
        rootOfTree = inputQueue.peek();
        currentPlace = rootOfTree;

        System.out.println();
        System.out.print("current text:");
        System.out.println();
        System.out.println(inputQueue);
        if (rootOfTree.typeTreePart) {
            System.out.printf(rootOfTree.symbolsText);
            System.out.println();
        } else {
            System.out.print(rootOfTree.operatorText);
            System.out.println();
        }
        System.out.println();

        inputQueue.poll();

        while(!inputQueue.isEmpty()){
            tempPart = inputQueue.peek();
            System.out.print("current text: ");
            System.out.println();
            if(!tempPart.typeTreePart){
                System.out.print(tempPart.operatorText);
                System.out.println();
            }else{
                System.out.print(tempPart.symbolsText);
                System.out.println();
            }
            System.out.println();

            if(!tempPart.typeTreePart){
                //operator
                System.out.println(tempPart.typeChild);
                if(tempPart.typeChild){
                    //if it is "!"
                    if(!currentPlace.typeChild && currentPlace.leftChild == null) {
                        currentPlace.leftChild = tempPart;
                        tempPart.parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }else if(!currentPlace.typeChild && currentPlace.leftChild != null && currentPlace.rightChild ==null){
                        currentPlace.rightChild = tempPart;
                        tempPart.parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }else if(currentPlace.typeChild){
                        currentPlace.singleChild = tempPart;
                        tempPart.parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }

                }else{
                    while(true){
                        if(rootOfTree.typeTreePart){
                            rootOfTree.parentPointer = tempPart;
                            tempPart.leftChild = rootOfTree;
                            rootOfTree = tempPart;
                            currentPlace = rootOfTree;
                            break;
                        }
                        if((tempPart.priority < currentPlace.priority) && (currentPlace != rootOfTree)){
                            currentPlace = currentPlace.parentPointer;
                            continue;
                        }
                        if((tempPart.priority < currentPlace.priority)&& (currentPlace == rootOfTree)){
                            tempPart.leftChild = rootOfTree;
                            rootOfTree.parentPointer = tempPart;
                            rootOfTree = tempPart;
                            currentPlace = tempPart;
                            break;
                        }
                        if(tempPart.priority >= currentPlace.priority){
                            if(!currentPlace.typeChild){
                                tempPart.leftChild = currentPlace.rightChild;
                                tempPart.parentPointer = currentPlace;
                                currentPlace.rightChild = tempPart;
                                currentPlace = tempPart;
                            }else{
                                tempPart.leftChild = currentPlace.singleChild;
                                tempPart.parentPointer = currentPlace;
                                currentPlace.singleChild = tempPart;
                                currentPlace = tempPart;
                            }


                            break;
                        }
                    }
                }

            }else{
                //symbol

                if((currentPlace.typeChild == null || !currentPlace.typeChild) && currentPlace.leftChild == null){
                    currentPlace.leftChild = tempPart;
                    tempPart.parentPointer = currentPlace;
                }else

                if(!currentPlace.typeChild && currentPlace.leftChild != null && currentPlace.rightChild == null){
                    currentPlace.rightChild = tempPart;
                    tempPart.parentPointer = currentPlace;
                }else

                if(currentPlace.typeChild){

                    currentPlace.singleChild = tempPart;
                    tempPart.parentPointer = currentPlace;
                }

            }
            //printTree(rootOfTree, 0);
            inputQueue.poll();
        }

        return rootOfTree;
    }
}