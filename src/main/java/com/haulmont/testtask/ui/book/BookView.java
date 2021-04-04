package com.haulmont.testtask.ui.book;

import com.haulmont.testtask.dao.BookDao;
import com.haulmont.testtask.dao.DaoServices;
import com.haulmont.testtask.entity.Book;
import com.haulmont.testtask.entity.Publisher;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;

public class BookView extends VerticalLayout {
    private DaoServices<Book> services = new BookDao();
    private ListDataProvider<Book> dataProvider = new ListDataProvider<>(services.findAll());
    private Grid<Book> grid = new Grid<>(Book.class);

    public BookView() {
        setSizeFull();
        configurationHeader();

        addComponent(grid);
        updateGridList();
        configureGrid();
    }

    // Buttons layout.
    private void configurationHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.addComponents(addButtonComponent(),
                updateButtonComponent(), deleteButtonComponent());
        addComponent(headerLayout);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("title", "author", "genre", "publisher", "year", "city");
        filterGridRow();
    }

    private void updateGridList() {
        grid.setDataProvider(dataProvider);
    }

    // Data filtering.
    private void filterGridRow() {
        HeaderRow headerRow = grid.appendHeaderRow();

        TextField titleFilterField = new TextField();
        titleFilterField.setSizeFull();
        titleFilterField.setPlaceholder("Filter by title...");
        titleFilterField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        headerRow.getCell("title").setComponent(titleFilterField);

        titleFilterField.addValueChangeListener(event ->
                dataProvider.addFilter(book -> StringUtils
                        .containsIgnoreCase(book.getTitle(),
                                titleFilterField.getValue())));
        titleFilterField.setValueChangeMode(ValueChangeMode.LAZY);

        TextField authorFilterField = new TextField();
        authorFilterField.setSizeFull();
        authorFilterField.setPlaceholder("Filter by author...");
        authorFilterField.addStyleName(ValoTheme.TEXTFIELD_SMALL);
        headerRow.getCell("author").setComponent(authorFilterField);

        authorFilterField.addValueChangeListener(event ->
                dataProvider.addFilter(book -> StringUtils
                        .containsIgnoreCase(book.getAuthor().toString(),
                                authorFilterField.getValue())));
        authorFilterField.setValueChangeMode(ValueChangeMode.LAZY);


        ComboBox<Publisher> publisherComboBox = new ComboBox<>();
        publisherComboBox.setItems(Publisher.values());
        publisherComboBox.setSizeFull();
        publisherComboBox.setPlaceholder("Filter by publisher...");
        publisherComboBox.setStyleName(ValoTheme.COMBOBOX_SMALL);
        headerRow.getCell("publisher").setComponent(publisherComboBox);

        publisherComboBox.addValueChangeListener(event ->
                dataProvider.addFilter(prescription -> {
                    if (publisherComboBox.getValue() != null) {
                        return StringUtils.containsIgnoreCase(prescription.getPublisher().toString(),
                                publisherComboBox.getValue().toString());
                    } else {
                        return true;
                    }
                }));


    }

    // Add button: opens the add window.
    private Component addButtonComponent() {
        Button button = new Button("Add");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().addWindow(new BookWindowAdd(dataProvider, services)));
        return button;
    }

    // Update selected row button: check if the selected row is checked,
    // if so, opens the update window.
    private Component updateButtonComponent() {
        Button button = new Button("Update");
        button.addStyleNames(ValoTheme.BUTTON_PRIMARY, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Book bookSelected = null;

            for (Book book : grid.getSelectedItems()) {
                bookSelected = book;
            }
            if(bookSelected != null) {
                getUI().addWindow(new BookWindowUpdate(dataProvider, services, bookSelected));
            } else {
                Notification.show("Warning: You did not select any row!",
                        "", Notification.Type.WARNING_MESSAGE );
            }
        });

        return button;
    }

    // Delete selected row button: check if the selected row is checked,
    // if so, delete the data and updates the table.
    private Component deleteButtonComponent() {
        Button button = new Button("Delete");
        button.addStyleNames(ValoTheme.BUTTON_DANGER, ValoTheme.BUTTON_SMALL);

        button.addClickListener(event -> {
            Book bookSelected = null;

            for (Book book : grid.getSelectedItems()) {
                bookSelected = book;
            }
            if (bookSelected != null) {
                try {
                    services.delete(bookSelected);
                    dataProvider.getItems().remove(bookSelected);
                    dataProvider.refreshAll();
                    Notification.show("Success!",
                            "", Notification.Type.HUMANIZED_MESSAGE);
                } catch (Exception exception) {
                    Notification.show("Error: " + exception, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        return button;
    }
}
