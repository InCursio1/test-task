package com.haulmont.testtask.ui.author;

import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Author;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class AuthorWindowAdd extends Window {
    private ListDataProvider<Author> dataProvider;
    private DaoServices<Author> services;

    private TextField firstNameTextField = new TextField("First name");
    private TextField middleNameTextField = new TextField("Middle name");
    private TextField lastNameTextField = new TextField("Last name");

    public AuthorWindowAdd(ListDataProvider<Author> dataProvider, DaoServices<Author> services) {
        this.dataProvider = dataProvider;
        this.services = services;
        setCaption("Add");
        setModal(true);
        setResizable(false);
        center();
        setContent(mainLayoutContent());
    }

    // Creates the main layout of the window.
    // Configures and adds components to the layout.
    private VerticalLayout mainLayoutContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidthFull();

        firstNameTextField.setPlaceholder("Александр");
        middleNameTextField.setPlaceholder("Сергеевич");
        lastNameTextField.setPlaceholder("Пушкин");

        mainLayout.addComponent(lastNameTextField);
        mainLayout.addComponent(firstNameTextField);
        mainLayout.addComponent(middleNameTextField);
        mainLayout.addComponents(addButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component addButtonWindow() {
        Button addButton = new Button("Ok");
        addButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        addButton.setWidthFull();

        // Validation of each form.
        Binder<Author> binder = new Binder<>();

        binder.forField(firstNameTextField)
                .withValidator(new BeanValidator(Author.class, "firstName"))
                .bind(Author::getFirstName, Author::setFirstName);

        binder.forField(middleNameTextField)
                .withValidator(new BeanValidator(Author.class, "middleName"))
                .bind(Author::getMiddleName, Author::setMiddleName);

        binder.forField(lastNameTextField)
                .withValidator(new BeanValidator(Author.class, "lastName"))
                .bind(Author::getLastName, Author::setLastName);

        // Сlick listener: if the data validation is successful -> add the data.
        addButton.addClickListener(event -> {
            if (binder.isValid()) {
                Author addAuthor = new Author(firstNameTextField.getValue(), middleNameTextField.getValue(),
                        lastNameTextField.getValue());
                services.save(addAuthor);
                dataProvider.getItems().add(addAuthor);
                dataProvider.refreshAll();
                close();
                Notification.show("Success!", "", Notification.Type.HUMANIZED_MESSAGE);
            } else {
                Notification.show("Warning: Check the date is correct!",
                        "", Notification.Type.WARNING_MESSAGE);
            }
        });
        return addButton;
    }

    // Button to cancel and close the update window.
    private Component cancelButtonWindow() {
        Button cancelButton = new Button("Cancel");
        cancelButton.addStyleNames(ValoTheme.BUTTON_DANGER);
        cancelButton.setWidthFull();

        cancelButton.addClickListener(event -> close());
        return cancelButton;
    }
}
