package com.haulmont.testtask.ui.book;

import com.haulmont.testtask.dao.AuthorDao;
import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.dao.GenreDao;
import com.haulmont.testtask.entity.Author;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Genre;
import com.haulmont.testtask.entity.Publisher;
import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class BookWindowAdd extends Window {
    private ListDataProvider<Book> dataProvider;
    private DaoServices<Book> bookServices;
    private DaoServices<Genre> genreServices = new GenreDao();
    private DaoServices<Author> authorServices = new AuthorDao();

    private TextField titleTextField = new TextField("Title");
    private ComboBox<Genre> genreComboBox = new ComboBox<>("Genre");
    private ComboBox<Author> authorComboBox = new ComboBox<>("Author");
    private ComboBox<Publisher> publisherComboBox = new ComboBox<>("Publisher");
    private TextField yearTextField = new TextField("Year");
    private TextField cityTextField = new TextField("City");


    public BookWindowAdd(ListDataProvider<Book> dataProvider, DaoServices<Book> bookServices) {
        this.dataProvider = dataProvider;
        this.bookServices = bookServices;
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

        titleTextField.setPlaceholder("Сияние");
        mainLayout.addComponent(titleTextField);

        authorComboBox.setItems(authorServices.findAll());
        authorComboBox.setItemCaptionGenerator(Author::toString);
        authorComboBox.setSizeFull();
        mainLayout.addComponent(authorComboBox);

        genreComboBox.setItems(genreServices.findAll());
        genreComboBox.setItemCaptionGenerator(Genre::toString);
        genreComboBox.setSizeFull();
        mainLayout.addComponent(genreComboBox);

        publisherComboBox.setItems(Publisher.values());
        publisherComboBox.setSizeFull();
        mainLayout.addComponent(publisherComboBox);

        yearTextField.setPlaceholder("1977");
        cityTextField.setPlaceholder("Нью-Йорк");

        mainLayout.addComponent(yearTextField);
        mainLayout.addComponent(cityTextField);

        mainLayout.addComponents(addButtonWindow(), cancelButtonWindow());

        return mainLayout;
    }

    private Component addButtonWindow() {
        Button addButton = new Button("Ok");
        addButton.addStyleNames(ValoTheme.BUTTON_FRIENDLY);
        addButton.setWidthFull();

        // Validation of each form.
        Binder<Book> binder = new Binder<>();

        binder.forField(titleTextField)
                .withValidator(new BeanValidator(Book.class, "title"))
                .bind(Book::getTitle, Book::setTitle);

        binder.forField(authorComboBox)
                .withValidator(new BeanValidator(Book.class, "author"))
                .bind(Book::getAuthor, Book::setAuthor);

        binder.forField(genreComboBox)
                .withValidator(new BeanValidator(Book.class, "genre"))
                .bind(Book::getGenre, Book::setGenre);

        binder.forField(publisherComboBox)
                .withValidator(new BeanValidator(Book.class, "publisher"))
                .bind(Book::getPublisher, Book::setPublisher);

        binder.forField(yearTextField)
                .withValidator(new BeanValidator(Book.class, "year"))
                .bind(Book::getYear, Book::setYear);


        binder.forField(cityTextField)
                .withValidator(new BeanValidator(Book.class, "city"))
                .bind(Book::getCity, Book::setCity);



        // Сlick listener: if the data validation is successful -> add the data.
        addButton.addClickListener(event -> {
            if (binder.isValid()) {
                Book addBook = new Book(titleTextField.getValue(),authorComboBox.getValue(), genreComboBox.getValue(),
                        publisherComboBox.getValue(), yearTextField.getValue(), cityTextField.getValue());
                bookServices.save(addBook);
                dataProvider.getItems().add(addBook);
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