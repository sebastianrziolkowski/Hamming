import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Main extends Application {

    public static void main(String args[]) {
        launch(args);
    }


    public void start(Stage stage) {
        Button buttonCoding = new Button("Coding");
        Button buttonDecoding = new Button("Decoding");
        HBox buttonBox = new HBox(buttonCoding, buttonDecoding);
        buttonBox.setPadding(new Insets(5, 0, 5, 90));
        buttonBox.setSpacing(20);


        Label textHx = new Label("H(x)=");
        Label text_description = new Label("Matrix coding / decoding (beware to input one number)");
        stage.setTitle("Hamming_Code");
        VBox vbox = new VBox();
        VBox mainVBox = new VBox();

        NumberTextField[] textFields1 = new NumberTextField[7];
        NumberTextField[] textFields2 = new NumberTextField[7];
        NumberTextField[] textFields3 = new NumberTextField[7];

        textHx.setPadding(new Insets(40, 0, 0, 0));
        text_description.setPadding(new Insets(0, 0, 0, 10));

        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();

        for (int i = 1; i <= 7; i++) {
            NumberTextField tf1 = new NumberTextField();
            NumberTextField tf2 = new NumberTextField();
            NumberTextField tf3 = new NumberTextField();
            tf1.setMaxWidth(30);
            tf2.setMaxWidth(30);
            tf3.setMaxWidth(30);


            hbox1.getChildren().addAll(tf1);
            hbox2.getChildren().addAll(tf2);
            hbox3.getChildren().addAll(tf3);

            hbox1.setPadding(new Insets(10, 0, 0, 20));
            hbox2.setPadding(new Insets(0, 0, 0, 20));
            hbox3.setPadding(new Insets(0, 0, 0, 20));

            textFields1[i - 1] = tf1;
            textFields2[i - 1] = tf2;
            textFields3[i - 1] = tf3;
        }

        setTextField1(textFields1);
        setTextField2(textFields2);
        setTextField3(textFields3);


        Label inputLabel = new Label("Input message(4 elements): ");
        NumberTextField inputTextField = new NumberTextField();
        inputTextField.setText("1011");
        HBox inputBox = new HBox(inputLabel, inputTextField);
        inputBox.setPadding(new Insets(30, 0, 0, 0));
        inputBox.setSpacing(10);


        Label resultLabel = new Label("Result");
        resultLabel.setPadding(new Insets(30, 0, 10, 150));


        Label outputLabel = new Label("Output: ");
        NumberTextField outputTextField = new NumberTextField();
        outputTextField.setText("");
        HBox outputBox = new HBox(outputLabel, outputTextField);
        outputBox.setPadding(new Insets(10, 0, 0, 0));
        outputBox.setSpacing(10);


        vbox.getChildren().addAll(hbox1, hbox2, hbox3);
        HBox hBoxH = new HBox(textHx, vbox);


        Label resultencodedLabel = new Label("Encoded result:");
        Label resultdecodedLabel = new Label("Decoded result:");
        Label resultencodedL = new Label("");
        Label resultdecodedL = new Label("");
        Label resultError = new Label("");


        HBox resultBox = new HBox();
        VBox encodedBox = new VBox();
        encodedBox.getChildren().addAll(resultencodedLabel, resultencodedL);
        VBox decodedBox = new VBox();
        decodedBox.getChildren().addAll(resultdecodedLabel, resultdecodedL, resultError);

        resultBox.getChildren().addAll(encodedBox, decodedBox);
        resultBox.setPadding(new Insets(10, 20, 0, 60));
        resultBox.setSpacing(30);


        mainVBox.getChildren().addAll(buttonBox, text_description, hBoxH, inputBox, resultLabel, outputBox, resultBox);
        Scene scene = new Scene(mainVBox, 385, 355);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();


        buttonCoding.setOnAction(event -> {
            String result = IntArrayToString(Code(oneDimensionalArray(inputTextField), twoDimensionalArray(textFields1, textFields2, textFields3)));
            resultencodedL.setText(result);
            outputTextField.setText(result);

            //cleaner
            resultError.setText("");
            resultdecodedL.setText("");
        });


        buttonDecoding.setOnAction(e -> {
            int[] Xn = oneDimensionalArray(outputTextField);
            int[] Yn = new int[7];
            System.arraycopy(Xn, 0, Yn, 0, 7);

            Random los = new Random();
            Random los_2 = new Random();
            int i = los_2.nextInt(4);
            if (los.nextInt(2) == 0)
                Yn[i] = (Yn[i] + 1) % 2;

            int[] resultArray = DeCode(twoDimensionalArray(textFields1, textFields2, textFields3), Yn);
            resultdecodedL.setText(IntArrayToString(resultArray));


            int[] YnMessage = new int[4];
            System.arraycopy(Yn, 0, YnMessage, 0, 4);


            if (Arrays.equals(resultArray, YnMessage)) {
                resultError.setText("No error.");
            } else {
                if (resultArray[0] == 1)
                    resultError.setText("Error at first byte.");
                if (resultArray[1] == 1)
                    resultError.setText("Error at second byte.");
                if (resultArray[2] == 1)
                    resultError.setText("Error at third byte.");
                if (resultArray[3] == 1)
                    resultError.setText("Error at fourth byte.");

            }

        });


    }


    //set codding matrix
    public void setTextField1(NumberTextField[] numberTextFields) {
        numberTextFields[0].setText("1");
        numberTextFields[1].setText("1");
        numberTextFields[2].setText("1");
        numberTextFields[3].setText("0");
        numberTextFields[4].setText("1");
        numberTextFields[5].setText("0");
        numberTextFields[6].setText("0");
    }

    public void setTextField2(NumberTextField[] numberTextFields) {
        numberTextFields[0].setText("0");
        numberTextFields[1].setText("1");
        numberTextFields[2].setText("1");
        numberTextFields[3].setText("1");
        numberTextFields[4].setText("0");
        numberTextFields[5].setText("1");
        numberTextFields[6].setText("0");
    }

    public void setTextField3(NumberTextField[] numberTextFields) {
        numberTextFields[0].setText("1");
        numberTextFields[1].setText("1");
        numberTextFields[2].setText("0");
        numberTextFields[3].setText("1");
        numberTextFields[4].setText("0");
        numberTextFields[5].setText("0");
        numberTextFields[6].setText("1");
    }


    public static class NumberTextField extends TextField {

        @Override
        public void replaceText(int start, int end, String text) {
            if (validate(text)) {
                super.replaceText(start, end, text);
            }
        }

        @Override
        public void replaceSelection(String text) {
            if (validate(text)) {
                super.replaceSelection(text);
            }
        }

        private boolean validate(String text) {
            return text.matches("[0-1]*");
        }

        private final IntegerProperty maxLength;

        public NumberTextField() {
            super();
            this.maxLength = new SimpleIntegerProperty(-1);
        }

        public IntegerProperty maxLengthProperty() {
            return this.maxLength;
        }

        public final Integer getMaxLength() {
            return this.maxLength.getValue();
        }

        public final void setMaxLength(Integer maxLength) {
            Objects.requireNonNull(maxLength, "Max length cannot be null, -1 for no limit");
            this.maxLength.setValue(maxLength);
        }
    }


    public static int[] oneDimensionalArray(NumberTextField inputTextField) {

        String string = inputTextField.getText();
        int inputArray[] = new int[inputTextField.getText().length()];

        for (int i = 0; i < inputArray.length; i++) {
            inputArray[i] = string.charAt(i) - '0';

        }
        return inputArray;

    }


    public int[][] twoDimensionalArray(NumberTextField[] inputTextField1, NumberTextField[] inputTextField2, NumberTextField[] inputTextField3) {
        int[] out_1 = new int[7];
        int[] out_2 = new int[7];
        int[] out_3 = new int[7];

        int output[][] = new int[3][7];
        for (int i = 0; i < 7; i++) {
            out_1[i] = Integer.parseInt(inputTextField1[i].getText());
            out_2[i] = Integer.parseInt(inputTextField2[i].getText());
            out_3[i] = Integer.parseInt(inputTextField3[i].getText());
        }

        output[0] = out_1;
        output[1] = out_2;
        output[2] = out_3;

        return output;
    }


    private String IntArrayToString(int[] array) {
        String strRet = "";
        for (int i : array) {
            strRet += Integer.toString(i);
        }
        return strRet;
    }


    protected static int[] Code(int[] Xk, int[][] H) {
        int n = H[0].length;
        int k = Xk.length;
        int r = n - k;

        int[] Xn = new int[n];

        System.arraycopy(Xk, 0, Xn, 0, k);
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < k; j++) {
                Xn[k + i] += H[i][j] * Xk[j];

            }
            Xn[k + i] %= 2;
        }
        return Xn;
    }


    protected static int[] DeCode(int[][] H, int[] Yn) {
        int n = Yn.length;
        int r = H.length;
        int k = n - r;
        int[] Xk = new int[k];
        int[] S = new int[r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < n; j++) {
                S[i] += H[i][j] * Yn[j];
            }
            S[i] %= 2;
        }
        if (wH(S) == 0) {

            System.arraycopy(Yn, 0, Xk, 0, k);
        } else {
            int ind = -1;
            for (int i = 0; i < k; i++) {
                boolean check = true;
                for (int j = 0; j < r; j++) {
                    if (H[j][i] != S[j]) {
                        check = false;
                        break;
                    }
                }
                if (check == true) {
                    ind = i;
                    break;
                }
            }
            Xk[ind] = (byte) ((Xk[ind] + 1) % 2);
        }
        return Xk;
    }


    protected static int wH(int[] S) {
        int sum = 0;
        for (int i = 0; i < S.length; i++) {
            sum += S[i];

        }
        return sum;

    }

}
