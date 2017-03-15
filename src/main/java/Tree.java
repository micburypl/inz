import java.util.Queue;

/**
 * Created on 14.03.2017.
 */
public class Tree {

    String word;
    Boolean nextCharFlag;
    Boolean errorFlag;
    Integer sizeOfString;
    Integer levelOfPriority;
    Boolean lastSymbol;
    Boolean eMessage;
    String line;
    TreePart rootOfTree;

    Queue<TreePart> inputQueue;

    public Tree() {
        word = "";
        nextCharFlag = false;
        errorFlag = false;
        sizeOfString = 0;
        lastSymbol = false;
        eMessage = false;
        line = "";

    }

    public void printQueue(Queue<TreePart> inputQueue) {
        System.out.println();
        System.out.print("Start print queue");
        System.out.println();
        System.out.print("Queue:");
        System.out.println();
        while (inputQueue.isEmpty()){
            TreePart t = inputQueue.peek();
            System.out.print(t.typeTreePart);
            if(!t.typeTreePart){
                System.out.print(" " + t.operatorText + " priority " + t.priority);
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


    TreePart createTree(Queue<TreePart> inputQueue){
        TreePart rootOfTree = new TreePart();
        TreePart currentPlace = new TreePart();
        TreePart tempPart = new TreePart();
        rootOfTree = inputQueue.peek();
        currentPlace = rootOfTree;

        System.out.println();
        System.out.print("current text:");
        System.out.println();
        if(!tempPart.typeTreePart){
            System.out.print(tempPart.operatorText);
            System.out.println();
        }else{
            System.out.printf(tempPart.symbolsText);
            System.out.println();
        }
        System.out.println();

        inputQueue.poll();

        printTree(rootOfTree, 0);

        while(inputQueue.isEmpty()){
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

                if(!currentPlace.typeChild && currentPlace.leftChild == null){
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
            printTree(rootOfTree, 0);
            inputQueue.poll();

//        cout<<"finish create tree"<<endl<<endl;
//        outputFile<<"finish create tree"<<endl<<endl;
        }

        return rootOfTree;
    }

    void printTree(TreePart rootOfTree, int level){
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
        //cout<< "operand " << word << endl;
        TreePart newTreePart = new TreePart();
        newTreePart.priority = prior + levelOfPriority;
        if(word.equals("!")){
            newTreePart.typeChild = true;
        }else{
            newTreePart.typeChild = false;
        }
        newTreePart.typeTreePart = false;
        newTreePart.operatorText = word;
        inputQueue.add(newTreePart);
        lastSymbol = false;
    }

    void createSymbol(String word){
        //cout<< "symbol " << word << endl;
        TreePart newTreePart = new TreePart();
        newTreePart.symbolsText = word;
        newTreePart.typeTreePart = true;
        inputQueue.add(newTreePart);
        lastSymbol = true;
    }

    Queue<TreePart> createQueue(String line){
        Queue<TreePart> empty = null;
        //inputQueue.swap(empty);
        empty.addAll(inputQueue);
        inputQueue = null;
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
                case '+':
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(1, "+");
                    break;
                case '-':
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(1, "-");
                    break;
                case '*':
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(2, "*");
                    break;
                case '/':
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(2, "/");
                    break;
                case '%':
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(2, "%");
                    break;
                case '^':
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(3, "^");
                    break;
                case '!':
                    if(word.length() != 0){
                        errorFlag = true;
                        break;
                    }
                    //word = "";
                    createOperand(6, "!");
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

                    if(i!=line.length()){
                        if((line.charAt(i+1) >= 'a' && line.charAt(i+1) <='z')||(line.charAt(i+1) >= 'A' && line.charAt(i+1) <='Z')||(line.charAt(i+1) >= '0' && line.charAt(i+1) <='9')){
                            errorFlag = true;
                            break;
                        }
                    }
                    levelOfPriority -= 10;
                    break;
                case '&':

                    if(i == line.length()-1){
                        errorFlag = true;
                        break;
                    }
                    if(line.charAt(i+1) == '&'){
                        nextCharFlag = true;
                    }else{
                        errorFlag = true;
                        break;
                    }
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(4, "&&");
                    break;
                case '|':
                    if(i == line.length()-1){
                        errorFlag = true;
                        break;
                    }
                    if(line.charAt(i+1) == '|'){
                        nextCharFlag = true;
                    }else{
                        errorFlag = true;
                        break;
                    }
                    if(word.length() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(5, "||");
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
            createSymbol(word);
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
            return null;
        }
        return inputQueue;
    }
}
