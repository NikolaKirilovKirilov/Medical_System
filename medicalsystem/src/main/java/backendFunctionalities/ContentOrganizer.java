package backendFunctionalities;

import com.example.model.*;
import customColors.ColorSchemes;
import uiComponents.Form;
import uiComponents.StyledComponents;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiConsumer;

public class ContentOrganizer {

    Color headerColor = ColorSchemes.MENU_GREEN, hoverBackgroundColor = Color.DARK_GRAY, hoverTextColor = Color.WHITE;
    Form formCaller;
    StyledComponents sc;

    public ContentOrganizer() {
        sc = new StyledComponents();
    }

    public JPanel sortEntries(JComboBox<String> comboBox, ArrayList<User> list, JPanel contentPanel, JPanel contentButtonPanel, String instance) {
        String option = Objects.requireNonNull(comboBox.getSelectedItem()).toString();

        Comparator<User> comparator = null;

        // Use instanceof to determine what we're sorting
        if (!list.isEmpty()) {
            User sample = list.get(0);

            if (sample instanceof Doctor) {
                switch (option) {
                    case "Код":
                        comparator = Comparator.comparing(user -> ((Doctor) user).getId());
                        break;
                    case "Име":
                        comparator = Comparator.comparing(user -> ((Doctor) user).getName(), String.CASE_INSENSITIVE_ORDER);
                        break;
                    case "Специализация":
                        comparator = Comparator.comparing(user -> ((Doctor) user).getSpecialization(), String.CASE_INSENSITIVE_ORDER);
                        break;
                }
            } else if (sample instanceof Patient) {
                switch (option) {
                    case "Код":
                        comparator = Comparator.comparing(user -> ((Patient) user).getId());
                        break;
                    case "Име":
                        comparator = Comparator.comparing(user -> ((Patient) user).getName(), String.CASE_INSENSITIVE_ORDER);
                        break;
                }
            } else if (sample instanceof Disease) {
                switch (option) {
                    case "Код":
                        comparator = Comparator.comparing(user -> ((Disease) user).getId());
                        break;
                    case "Име":
                        comparator = Comparator.comparing(user -> ((Disease) user).getName(), String.CASE_INSENSITIVE_ORDER);
                        break;
                }
            } else if (sample instanceof Medication) {
                switch (option) {
                    case "Код":
                        comparator = Comparator.comparing(user -> ((Medication) user).getId());
                        break;
                    case "Име":
                        comparator = Comparator.comparing(user -> ((Medication) user).getName(), String.CASE_INSENSITIVE_ORDER);
                        break;
                }
            }
        }

        if (comparator != null) {
            list.sort(comparator);
            return refreshEntriesDisplay(list, contentPanel, contentButtonPanel, instance);  // Redraw the UI
        }
        return null;
    }

    private JPanel refreshEntriesDisplay(ArrayList<User> entries, JPanel contentPanel, JPanel contentButtonPanel, String instance) {
        contentPanel.removeAll();

        contentButtonPanel.removeAll();

        JButton insertButton = sc.createStyledButton("Записване на: " + instance, headerColor, hoverBackgroundColor, hoverTextColor);
        JButton deleteButton = sc.createStyledButton("Изтриване на: " + instance, headerColor, hoverBackgroundColor, hoverTextColor);

        switch (instance) {
            case "Пациент":
                insertButton.addActionListener(e -> formCaller.patientInsertion());
                deleteButton.addActionListener(e -> formCaller.patientDeletion());
                break;
            case "Доктор":
                insertButton.addActionListener(e -> formCaller.doctorInsertion());
                deleteButton.addActionListener(e -> formCaller.doctorDeletion());
                break;
            case "Заболяване":
                insertButton.addActionListener(e -> formCaller.diseaseInsertion());
                deleteButton.addActionListener(e -> formCaller.diseaseDeletion());
                break;
            case "Медикамент":
                insertButton.addActionListener(e -> formCaller.medicationInsertion());
                deleteButton.addActionListener(e -> formCaller.medicationDeletion());
                break;
        }

        contentButtonPanel.add(insertButton);
        contentButtonPanel.add(deleteButton);
        contentPanel.add(contentButtonPanel);

        for (User entry : entries) {
            JPanel entryPanel = new JPanel(new GridBagLayout());
            entryPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            entryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            GridBagConstraints entriesGbc = new GridBagConstraints();
            entriesGbc.insets = new Insets(2, 0, 2, 2);
            entriesGbc.anchor = GridBagConstraints.EAST;
            entriesGbc.gridx = 0;
            entriesGbc.gridy = 0;

            BiConsumer<String, String> addLabelPair = (label, value) -> {
                entriesGbc.gridx = 0;
                entriesGbc.anchor = GridBagConstraints.EAST;
                entryPanel.add(new JLabel(label), entriesGbc);

                entriesGbc.gridx = 1;
                entriesGbc.anchor = GridBagConstraints.WEST;
                entryPanel.add(new JLabel(value), entriesGbc);

                entriesGbc.gridy++;
            };

            if (entry instanceof Doctor doc) {
                addLabelPair.accept("Код на Доктор:", String.valueOf(doc.getId()));
                addLabelPair.accept("Име на Доктор:", doc.getName());
                addLabelPair.accept("Фамилия на Доктор:", doc.getSurname());
                addLabelPair.accept("Специализация:", doc.getSpecialization());
            } else if (entry instanceof Patient pat) {
                addLabelPair.accept("Код на Пациент:", String.valueOf(pat.getId()));
                addLabelPair.accept("Име на Пациент:", pat.getName());
                addLabelPair.accept("Фамилия на Пациент:", pat.getSurname());
            } else if (entry instanceof Disease dis) {
                addLabelPair.accept("Код на Заболяване:", String.valueOf(dis.getId()));
                addLabelPair.accept("Име на Заболяване:", dis.getName());
                addLabelPair.accept("Описание на Заболяване:", dis.getDescription());
                addLabelPair.accept("Третиране на Заболяване:", dis.getTreatment());
            } else if (entry instanceof Medication med) {
                addLabelPair.accept("Код на Медикамент:", String.valueOf(med.getId()));
                addLabelPair.accept("Име на Медикамент:", med.getName());
                addLabelPair.accept("Описание на Медикамент:", med.getDescription());
                addLabelPair.accept("Дозировка на Медикамент:", med.getDosage());
            }

            contentPanel.add(Box.createVerticalStrut(10));
            contentPanel.add(entryPanel);
        }
        return contentPanel;
    }
}
