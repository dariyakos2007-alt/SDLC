package by.bsuir.morse.controller;

import by.bsuir.morse.model.InvalidMorseCodeException;
import by.bsuir.morse.model.MorseDecoderModel;
import by.bsuir.morse.view.InputDialog;
import by.bsuir.morse.view.MainView;

import javax.swing.*;

public class MorseController {

    private final MorseDecoderModel model;
    private final MainView view;
    private InputDialog inputDialog;

    public MorseController(MorseDecoderModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    public void openInputDialog(JFrame parent) {
        if (inputDialog == null || !inputDialog.isDisplayable()) {
            inputDialog = new InputDialog(parent);
            inputDialog.setOnSubmit(this::onSubmit);
        }

        inputDialog.setValues(model.getLastEncodedText());

        view.setLastInput(model.getLastEncodedText());
        view.setResult(model.getDecodedText());

        inputDialog.setVisible(true);
    }

    private void onSubmit() {
        String input = inputDialog.getInput();
        try {
            model.decode(input);
            view.setLastInput(model.getLastEncodedText());
            view.setResult(model.getDecodedText());
            inputDialog.dispose();
        } catch (InvalidMorseCodeException e) {
            inputDialog.showError(e.getMessage());
        }
    }
}