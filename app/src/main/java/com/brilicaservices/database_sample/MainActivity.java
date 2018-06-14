package com.brilicaservices.database_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;





public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    static final String TAG = MainActivity.class.getName();

    EditText studentNameTF, studentPhoneTF, studentAddressTF;
    Button addStudentBtn, displayStudentBtn;
    TextView displayStudentsResultTV;

    /*
     * Creating a global collegeName String object.*/
    String collegeName = "";

    /*
     * ArrayList of Student class will be used
     * to store the data of individual student.*/
    ArrayList<StudentModel> studentArrayList = new ArrayList<>();

    /*
     * Creating a spinner object*/
    Spinner spinnerCollegeNames;

    /*
     * Creating a string of array of colleges*/
    String collegeNames[] = {"Select college name", "DIT", "Graphic Era", "HNB", "UPES"};

    /*
    * Database Helper*/
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * casting objects with the respective view ids.*/
        studentNameTF = (EditText) findViewById(R.id.student_name);
        studentPhoneTF = (EditText) findViewById(R.id.enter_phone);
        studentAddressTF = (EditText) findViewById(R.id.enter_address);

        addStudentBtn = (Button) findViewById(R.id.add_student_button);
        displayStudentBtn = (Button) findViewById(R.id.display_student_button);

        displayStudentsResultTV = (TextView) findViewById(R.id.display_student_details_text_view);

        spinnerCollegeNames = (Spinner) findViewById(R.id.college_name_spinner);

        databaseHelper = new DatabaseHelper(this);

        /*
         * Using setOnItemSelectedListener on spinner object
         * and giving it the context - this, meaning current activity*/
        spinnerCollegeNames.setOnItemSelectedListener(this);

        /*
         * Creating an arrayAdapter object and passing 3 different arguments
         * i.e. context, layout, array*/
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, collegeNames);

        /*
         * using the spinner's setAdapter method to update it's adapter*/
        spinnerCollegeNames.setAdapter(arrayAdapter);

        /*
         * setPrompt is select on spinner to just give the refernce that the
         * first object of array is only a label.*/
        spinnerCollegeNames.setPrompt(collegeNames[0]);

        /*
         * Adding a click listener on addStudentBtn*/
        addStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                 * On the click of the button, getting values from
                 * the user input.*/
                String name = studentNameTF.getText().toString();
                long phone = Long.parseLong(studentPhoneTF.getText().toString());
                String address = studentAddressTF.getText().toString();

                /*
                 * Storing the new values into the arrayList using the
                 * Student class object.*/
                databaseHelper.addNewStudent(new StudentModel(name, collegeName, address, phone));

                /*
                 * Showing a success message once the data has been saved into arrayList*/
                Toast.makeText(getApplicationContext(), "Student data saved successfully", Toast.LENGTH_LONG).show();
            }
        });

        /*
         * Display button used to display the data of students*/
        displayStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                 * Using a for loop to iterate through the list of
                 * objects that are stored in the arrayList.*/
                studentArrayList.addAll(databaseHelper.allStudentsDetails());
                for (int i = 0; i < studentArrayList.size(); i++) {

                    /*
                     * Displaying the results in the displayStudentsResultTV Text View.
                     * get the reference of the value by studentArrayList.get(i) - object stored at current location
                     * .studentName, .studentCollege will give us the value of each variable
                     * in that particular object.*/

                    displayStudentsResultTV.setText(displayStudentsResultTV.getText() +
                            "Student ID is: " + studentArrayList.get(i).getId() + "\n");
                    displayStudentsResultTV.setText(displayStudentsResultTV.getText() +
                            "Student Name is: " + studentArrayList.get(i).getName() + "\n");
                    displayStudentsResultTV.setText(displayStudentsResultTV.getText() +
                            "Student College is: " + studentArrayList.get(i).getCollegeName() + "\n");
                    displayStudentsResultTV.setText(displayStudentsResultTV.getText() +
                            "Student Phone Number is: " + studentArrayList.get(i).getPhoneNumber() + "\n");
                    displayStudentsResultTV.setText(displayStudentsResultTV.getText() +
                            "Student Address is: " + studentArrayList.get(i).getAddress() + "\n");
                    displayStudentsResultTV.setText(displayStudentsResultTV.getText() + "****************\n\n");

//                    Log.d(TAG, studentArrayList.get(i).studentName + "\n");
//                    Log.d(TAG, studentArrayList.get(i).studentCollege + "\n");
//                    Log.d(TAG, studentArrayList.get(i).studentPhoneNumber+ "\n");
//                    Log.d(TAG, studentArrayList.get(i).studentAddress + "\n");
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*
         * getting college name from the list of colleges.
         * It will be updated only on the basis of array.*/
        collegeName = collegeNames[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
