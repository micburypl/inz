/**
 * Created by Misiek on 14.03.2017.
 */
public class oldThink {
    /*
    #include <iostream>
#include <fstream>
#include <string>
#include <queue>

    using namespace std;
    struct treePart{
        bool typeChild;
        bool typeTreePart;
        treePart* rightChild;
        treePart* leftChild;
        treePart* singleChild;
        treePart* parentPointer;
        int priority;
        string operatorText;
        string symbolsText;
    };
    class tree{

        public:

        treePart* createTree(queue<treePart*> inputQueue);
        treePart* rootOfTree = new treePart();

        void createSymbol(string word);
        void createOperand(int prior, string word);
        void printTree(treePart* rootOfTree, int level);
        queue<treePart*> createQueue(string line);

    };

    void createFile();
    void printQueue(queue<treePart*> inputQueue);
    queue<treePart*> inputQueue;
    string word;
    bool nextCharFlag;
    bool errorFlag;
    int sizeOfString;
    int levelOfPriority;
    bool lastSymbol;
    bool eMessage;
    string line;
    fstream outputFile("outputFile.txt");
    fstream errorFile("errorFile.txt");
    string loadFromFile;
    int main() {

        tree myTree ;


        cout<<"Do you want to create file via program? (y)"<<endl;
        cin>>loadFromFile;
        if(loadFromFile == "y"){
            createFile();
        }


        ifstream inputFile;
        inputFile.open("inputFile.txt");



        if (inputFile.is_open()) {
            while (getline(inputFile, line)) {

                inputQueue = myTree.createQueue(line);
                if(inputQueue.size() > 0){

                    cout<<"---------------------------"<<endl<<"input line: "<<line<<endl;
                    outputFile<<"---------------------------"<<endl<<"input line: "<<line<<endl;

                    cout<<"correct input: "<<endl<<endl<<line<<endl<<endl;
                    outputFile<<"correct input: "<<endl<<endl<<line<<endl<<endl;
                    printQueue(inputQueue);

                    myTree.rootOfTree =  myTree.createTree(inputQueue);

                    cout<<endl<<"start print final tree"<<endl<<endl;
                    outputFile<<endl<<"start print final tree"<<endl<<endl;

                    myTree.printTree( myTree.rootOfTree, 0);

                    cout<<"end print final tree"<<endl<<endl;
                    outputFile<<"end print final tree"<<endl<<endl;
                }
            }
            inputFile.close();
        }else{
            cout << "Unable to open file";
            errorFile<< "Unable to open file";
        }
        getchar();
        getchar();
        return 0;
    };

    void createFile(){
        fstream o("inputFile.txt", std::ofstream::out | std::ofstream::trunc);
        o<<"t e s t 1 - t e s t 2 * t e s t 3" <<endl;
        o<<"test1 * test2 - test3" <<endl;
        o<<"TE ST*T EST/TES T" <<endl;
        o<<"qa1!asfa^234  fgg"<<endl;
        o<<"aaa&&bbb||ccc"<<endl;
        o<<"aaa&bbb|ccc"<<endl;
        o<<"aaa+-bbb"<<endl;
        o<<"(a(b)c)"<<endl;
        o<<"(a+c)"<<endl;
        o<<"a(ac)"<<endl;
        o<<"a*(ac)"<<endl;
        o<<"(a+c)b"<<endl;
        o<<"(a+c)*"<<endl;
        o<<"(a+c)*b"<<endl;
        o<<"a+(a+c)/c"<<endl;
        o<<"(a+(a+c))/c"<<endl;
        o<<"(a+b)&&(a*b)||(a-b)"<<endl;
        o<<"b+!a+b"<<endl;
        o<<"bb!a*b"<<endl;
        o<<"!aaaaa"<<endl;
        o<<"(a+b)^(a*c)"<<endl;
        o<<"!(a*b)"<<endl;
        o<<"a+b"<<endl;
        o<<"a+b*c"<<endl;
        o<<"(a+b)*c"<<endl;
        o<<"a+b-c"<<endl;
        o<<"A+B^C"<<endl;
        o<<"(A+B)^C"<<endl;
        o<<"(A+B)^C*D"<<endl;
        o<<"a^!b"<<endl;
        o<<"abcd && !1234 ^ (ddd34 + eeee11) || fgh12 * ii23"<<endl;



        o.close();
        return;
    }

    void tree::createSymbol(string word){
        //cout<< "symbol " << word << endl;
        treePart* newTreePart = new treePart();
        newTreePart->leftChild = NULL;
        newTreePart->rightChild = NULL;
        newTreePart->singleChild = NULL;
        newTreePart->parentPointer = NULL;
        newTreePart->symbolsText = word;
        newTreePart->typeTreePart = true;
        inputQueue.push(newTreePart);
        lastSymbol = true;
        word.empty();
    }

    void tree::createOperand(int prior, string word){
        //cout<< "operand " << word << endl;
        treePart* newTreePart = new treePart;
        newTreePart->leftChild = NULL;
        newTreePart->rightChild = NULL;
        newTreePart->singleChild = NULL;
        newTreePart->parentPointer = NULL;
        newTreePart->priority = prior + levelOfPriority;
        if(word == "!"){
            newTreePart->typeChild = true;
        }else{
            newTreePart->typeChild = false;
        }
        newTreePart->typeTreePart = false;
        newTreePart->operatorText = word;
        inputQueue.push(newTreePart);
        lastSymbol = false;
    }

    void printQueue(queue<treePart*> inputQueue){
        cout<<endl<<"start print queue"<<endl;
        cout<<"queue:"<<endl;
        while (!inputQueue.empty()){
            treePart* t = inputQueue.front();
            cout<< t->typeTreePart;
            if(t->typeTreePart == false){
                cout<<" "<<t->operatorText<<" priority "<<t->priority<<endl;
            }else{
                cout<<" "<<t->symbolsText<<endl;
            }
            inputQueue.pop();
        }
        cout<<"end print queue"<<endl<<endl;
    }

    queue<treePart*> tree::createQueue(string line){
        queue<treePart*> empty;
        inputQueue.swap(empty);
        word = "";
        levelOfPriority = 0;
        errorFlag = false;
        lastSymbol = false;
        eMessage = false;
        sizeOfString = line.size();

        for(int i = 0; i<sizeOfString;i++){

            if(nextCharFlag == true){
                nextCharFlag = false;
                continue;
            }
            if((line[i] >= 'a' && line[i] <='z')||(line[i] >= 'A' && line[i] <='Z')||(line[i] >= '0' && line[i] <='9')){
                word+=line[i];
                continue;
            }
            if(line[i] == ' '){
                continue;
            }
            switch(line[i]){
                case '+':
                    if(word.size() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(1, "+");
                    break;
                case '-':
                    if(word.size() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(1, "-");
                    break;
                case '*':
                    if(word.size() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(2, "*");
                    break;
                case '/':
                    if(word.size() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(2, "/");
                    break;
                case '%':
                    if(word.size() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(2, "%");
                    break;
                case '^':
                    if(word.size() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(3, "^");
                    break;
                case '!':
                    if(word.size() != 0){
                        errorFlag = true;
                        break;
                    }
                    //word = "";
                    createOperand(6, "!");
                    break;
                case '(':
                    if(i>0){
                        if((line[i-1] >= 'a' && line[i-1] <='z')||(line[i-1] >= 'A' && line[i-1] <='Z')||(line[i-1] >= '0' && line[i-1] <='9')){
                            errorFlag = true;
                            break;
                        }
                    }
                    levelOfPriority += 10;
                    break;
                case ')':

                    if(i!=line.size()){
                        if((line[i+1] >= 'a' && line[i+1] <='z')||(line[i+1] >= 'A' && line[i+1] <='Z')||(line[i+1] >= '0' && line[i+1] <='9')){
                            errorFlag = true;
                            break;
                        }
                    }
                    levelOfPriority -= 10;
                    break;
                case '&':

                    if(i == line.size()-1){
                        errorFlag = true;
                        break;
                    }
                    if(line[i+1] == '&'){
                        nextCharFlag = true;
                    }else{
                        errorFlag = true;
                        break;
                    }
                    if(word.size() == 0){
                        errorFlag = true;
                        break;
                    }
                    createSymbol(word);
                    word = "";
                    createOperand(4, "&&");
                    break;
                case '|':
                    if(i == line.size()-1){
                        errorFlag = true;
                        break;
                    }
                    if(line[i+1] == '|'){
                        nextCharFlag = true;
                    }else{
                        errorFlag = true;
                        break;
                    }
                    if(word.size() == 0){
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
            if(errorFlag == true){

                cout<<"---------------------------"<<endl<<"input line: "<<line<<endl;
                errorFile<<"---------------------------"<<endl<<"input line: "<<line<<endl;

                cout << "error input line: " << line << endl;
                errorFile << "error input line: " << line << endl;
                eMessage = true;
                break;
            }
        }

        if(levelOfPriority != 0&&errorFlag != true){
            cout << "Wrong Bracket"<< endl;
            errorFile << "Wrong Bracket"<< endl;
            errorFlag = true;
        }

        if(word.size() > 0){
            createSymbol(word);
            word = "";
        }
        if(lastSymbol == false && eMessage == false){
            errorFlag = true;
            cout<<"---------------------------"<<endl<<"input line: "<<line<<endl;
            errorFile<<"---------------------------"<<endl<<"input line: "<<line<<endl;
            cout << "error input line: " << line << endl;
            errorFile << "error input line: " << line << endl;
        }
        if(errorFlag == true){
            queue<treePart*> empty;
            return empty;
        }
        return inputQueue;
    }

    void tree::printTree(treePart* rootOfTree, int level){
        for(int i = 0; i < level; i++){
            cout<<"->";
            outputFile<<"->";
        }
        if(rootOfTree->typeTreePart == true){
            cout<<rootOfTree->symbolsText<<endl;
            outputFile<<rootOfTree->symbolsText<<endl;
            return;
        }
        cout<<rootOfTree->operatorText<<endl;
        outputFile<<rootOfTree->operatorText<<endl;
        if(rootOfTree->typeChild == true){
            if(rootOfTree->singleChild != NULL){
                printTree(rootOfTree->singleChild, level+1);
            }

        }else{
            if(rootOfTree->leftChild != NULL){
                printTree(rootOfTree->leftChild, level+1);
            }
            if(rootOfTree->rightChild != NULL){
                printTree(rootOfTree->rightChild, level+1);
            }
        }
        return;
    }

    treePart* tree::createTree(queue<treePart*> inputQueue){
        treePart* rootOfTree = new treePart();
        treePart* currentPlace = new treePart();
        treePart* tempPart = new treePart();
        rootOfTree = inputQueue.front();
        currentPlace = rootOfTree;

        cout<<endl<<"current text: "<<endl;
        outputFile<<endl<<"current text: "<<endl;
        if(tempPart->typeTreePart != true){
            cout<<tempPart->operatorText<<endl;
            outputFile<<tempPart->operatorText<<endl;
        }else{
            cout<<tempPart->symbolsText<<endl;
            outputFile<<tempPart->symbolsText<<endl;
        }
        cout<<endl;
        outputFile<<endl;

        inputQueue.pop();

        printTree(rootOfTree, 0);

        while(!inputQueue.empty()){
            tempPart = inputQueue.front();
            cout<<endl<<"current text: "<<endl;
            outputFile<<endl<<"current text: "<<endl;
            if(tempPart->typeTreePart != true){
                cout<<tempPart->operatorText<<endl;
                outputFile<<tempPart->operatorText<<endl;
            }else{
                cout<<tempPart->symbolsText<<endl;
                outputFile<<tempPart->symbolsText<<endl;
            }
            cout<<endl;
            outputFile<<endl;

            if(tempPart->typeTreePart == false){
                //operator
                if(tempPart->typeChild == true){
                    //if it is "!"
                    if(currentPlace->typeChild == false && currentPlace->leftChild == NULL) {
                        currentPlace->leftChild = tempPart;
                        tempPart->parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }else if(currentPlace->typeChild == false && currentPlace->leftChild != NULL && currentPlace->rightChild ==NULL){
                        currentPlace->rightChild = tempPart;
                        tempPart->parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }else if(currentPlace->typeChild == true){
                        currentPlace->singleChild = tempPart;
                        tempPart->parentPointer = currentPlace;
                        currentPlace = tempPart;
                    }

                }else{
                    while(true){
                        if(rootOfTree->typeTreePart == true){
                            rootOfTree->parentPointer = tempPart;
                            tempPart->leftChild = rootOfTree;
                            rootOfTree = tempPart;
                            currentPlace = rootOfTree;
                            break;
                        }
                        if((tempPart->priority < currentPlace->priority) && (currentPlace != rootOfTree)){
                            currentPlace = currentPlace->parentPointer;
                            continue;
                        }
                        if((tempPart->priority < currentPlace->priority)&& (currentPlace == rootOfTree)){
                            tempPart->leftChild = rootOfTree;
                            rootOfTree->parentPointer = tempPart;
                            rootOfTree = tempPart;
                            currentPlace = tempPart;
                            break;
                        }
                        if(tempPart->priority >= currentPlace->priority){
                            if(currentPlace->typeChild == false){
                                tempPart->leftChild = currentPlace->rightChild;
                                tempPart->parentPointer = currentPlace;
                                currentPlace->rightChild = tempPart;
                                currentPlace = tempPart;
                            }else{
                                tempPart->leftChild = currentPlace->singleChild;
                                tempPart->parentPointer = currentPlace;
                                currentPlace->singleChild = tempPart;
                                currentPlace = tempPart;
                            }


                            break;
                        }
                    }
                }


            }else{
                //symbol

                if(currentPlace->typeChild == false && currentPlace->leftChild == NULL){
                    currentPlace->leftChild = tempPart;
                    tempPart->parentPointer = currentPlace;
                }else

                if(currentPlace->typeChild == false && currentPlace->leftChild != NULL && currentPlace->rightChild == NULL){
                    currentPlace->rightChild = tempPart;
                    tempPart->parentPointer = currentPlace;
                }else

                if(currentPlace->typeChild == true){

                    currentPlace->singleChild = tempPart;
                    tempPart->parentPointer = currentPlace;
                }

            }
            printTree(rootOfTree, 0);
            inputQueue.pop();

//        cout<<"finish create tree"<<endl<<endl;
//        outputFile<<"finish create tree"<<endl<<endl;
        }

        return rootOfTree;
    }
    */
}
