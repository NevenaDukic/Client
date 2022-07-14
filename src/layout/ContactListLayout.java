package layout;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import entity.Contact;
import entity.User;
import interfaces.ContactListInterface;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ContactListLayout extends VBox {

	
	private TableView table;
	private TableColumn contact;
	private Button addContact;
	public final ObservableList<Contact> contacts = FXCollections.observableArrayList();
	private ContactListInterface contactListInterface;
	
	private List listContact;
	
	private String contactEmail;
	public ContactListLayout(int dim) {
		setSpacing(dim);
		init();
	}
	private void init() {
		initComponents();
		initLayout();
		initAction();
	}
	private void initComponents() {
		table = new TableView<User>();
		contact = new TableColumn<>("Contacts:");
		addContact = new Button("ADD CONTACT");
	}
	private void initLayout() {
		contact.setMinWidth(300);
		contact.setCellValueFactory(new PropertyValueFactory<Contact,String>("email"));
		table.getColumns().add(contact);
		table.setItems(contacts);
		getChildren().addAll(table,addContact);
	}
	public void showAlert() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Add contact:");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			contactEmail = result.get();
			contactListInterface.checkContact(result.get());
		   // chatInterface.sendEmailForChat(result.get());
		}
		
	}
	public void ErrorAdingContact() {
		Alert a = new Alert(AlertType.WARNING);
		a.setContentText("Can't add yourself as contact!");
		a.showAndWait();
	}
	private void initAction() {
		addContact.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				showAlert();
				
			}
		});
		
		table.setOnMouseClicked((MouseEvent event) -> {
	       System.out.println("KLIKNUO JE NA:" + table.getSelectionModel().getSelectedItem().toString());
	       Contact contact = (Contact) table.getSelectionModel().getSelectedItem();
	       contactListInterface.startChat(contact.getEmail());
	    });
	}	
	
	public void addContacts(ArrayList<String>contactsList) {
		
		int length = contactsList.size();
		int k = 0;
		while (k<length) {
			System.out.println("Contact je: " + contactsList.get(k));
			contacts.add(new Contact(contactsList.get(k), null));
			k++;
		}
	}
	private void showAlert1(String message) {
		Alert a = new Alert(AlertType.INFORMATION);
		a.setContentText(message);
		a.showAndWait();
	}
	private void addContactToList() {
		System.out.println("Dodao je userra!");
		contacts.add(new Contact(contactEmail,null));
		
	}
	public void addContact(int status) {
		if (status==0) {
			showAlert1("User with entered email does not exsist!");
		}else {
			addContactToList();
			showAlert1("NASAO USERA!");
		}
		
	}
	public void setContactListInterface(ContactListInterface contactListInterface) {
		this.contactListInterface = contactListInterface;
	}
	
	
}
