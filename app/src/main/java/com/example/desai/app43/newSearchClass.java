package com.example.desai.app43;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class newSearchClass extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

        public static final String USER_INDEX = "userindex";
        private AlbumUsers albumUsers;
        private User currentUser;
        private int userIndex;
        private Spinner spinner;
        private AutoCompleteTextView personTextView;
        private AutoCompleteTextView locationTextView;

        private Button searchButton;
        private Button cancelButton;
        private List<Photos> searchList;
        private ArrayList<String> allLocation;
        private ArrayList<String> allPerson;
        private AutoCompleteTextView auto2;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.new_search);

            final Bundle bundle = getIntent().getExtras();
            bundle.getInt(USER_INDEX);

            userIndex = bundle.getInt(USER_INDEX);
            allLocation = new ArrayList<>();
            allPerson = new ArrayList<>();

            albumUsers = AlbumUsers.loadFromDisk(this);
            currentUser = albumUsers.getUsers().get(userIndex);
            searchList = new ArrayList<>();

            Log.d("SEARCHTAG",userIndex+"");

            spinner = (Spinner) findViewById(R.id.andOrSpinner);
            searchButton = (Button) findViewById(R.id.dialogSearch);
            cancelButton = (Button) findViewById(R.id.tagCancel);
            personTextView = (AutoCompleteTextView) findViewById(R.id.personTextView);
            locationTextView = (AutoCompleteTextView) findViewById(R.id.locationTextView);








            for(int i=0;i<currentUser.getAlbums().size();i++){
                for(int j = 0;j < currentUser.getAlbums().get(i).getListofphotos().size();j++){
                    for(int x=0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
                        if(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location")){
                            allLocation.add(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue());
                        }
                    }


                }
            }

            for(int i=0;i<currentUser.getAlbums().size();i++){
                for(int j = 0;j < currentUser.getAlbums().get(i).getListofphotos().size();j++){
                    for(int x=0;x<currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size();x++){
                        if(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person")){
                            allPerson.add(currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue());
                        }
                    }


                }
            }

            // Log.d("ARRAYLISt",currentUser.getAlbums().get(0).getListofphotos().get(0).getLocationlist().size()+"");
            Log.d("ARRAYLIST",allLocation.size()+"");
            Log.d("ARRAYLISt",allPerson.size()+"");

            spinner.setOnItemSelectedListener(this);
            List<String> categories = new ArrayList<String>();
            categories.add("OR");
            categories.add("AND");




            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner.setAdapter(dataAdapter);






            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, final long id) {
            final String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

            ArrayAdapter<String> personAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,allPerson);
            personTextView.setAdapter(personAdapter);

            ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,allLocation);
            locationTextView.setAdapter(locationAdapter);
            searchList = new ArrayList<>();

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(item.equalsIgnoreCase("or")){
                        searchList.clear();
                        if(personTextView.getText().toString().isEmpty() && locationTextView.getText().toString().isEmpty()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(newSearchClass.this);
                            builder.setMessage("Field cannot be empty");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }


                            });
                            builder.show();
                            //works

                        }
                        else {
                            searchList.clear();
                            if (!personTextView.getText().toString().isEmpty()) {
                                for (int i = 0; i < currentUser.getAlbums().size(); i++) {
                                    for (int j = 0; j < currentUser.getAlbums().get(i).getListofphotos().size(); j++) {
                                        for (int x = 0; x < currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size(); x++) {
                                            if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(personTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person"))) {
                                                if(!searchList.contains(currentUser.getAlbums().get(i).getListofphotos().get(j))){
                                                    searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));}
                                            } else if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().startsWith(personTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person"))) {
                                                if(!searchList.contains(currentUser.getAlbums().get(i).getListofphotos().get(j))){
                                                    searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));}
                                            }
                                            //need to implement startswith();
                                        }
                                    }
                                }


                            }
                            if (!locationTextView.getText().toString().isEmpty()) {

                                for (int i = 0; i < currentUser.getAlbums().size(); i++) {
                                    for (int j = 0; j < currentUser.getAlbums().get(i).getListofphotos().size(); j++) {
                                        for (int x = 0; x < currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size(); x++) {
                                            if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(locationTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location"))) {
                                               if(!searchList.contains(currentUser.getAlbums().get(i).getListofphotos().get(j))){
                                                searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));}
                                            } else if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().startsWith(locationTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location"))) {
                                                if(!searchList.contains(currentUser.getAlbums().get(i).getListofphotos().get(j))){
                                                    searchList.add(currentUser.getAlbums().get(i).getListofphotos().get(j));}
                                            }
                                            //need to implement startswith();
                                        }
                                    }
                                }

                                //locationTextView.setText("");


                            }

                        }
                        Log.d("SEARCHRESULTS1",searchList.size() + " ");
                    }
                    else if(item.equalsIgnoreCase("and")){
                        searchList.clear();
                        if(personTextView.getText().toString().isEmpty() && locationTextView.getText().toString().isEmpty()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(newSearchClass.this);
                            builder.setMessage("Field cannot be empty");

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    return;
                                }


                            });
                            builder.show();
                            //works

                        }
                        else if(!personTextView.getText().toString().isEmpty() && !locationTextView.getText().toString().isEmpty() ){
                            String person = personTextView.getText().toString();
                            String location =locationTextView.getText().toString();
                            List<Photos> loc = new ArrayList<>();
                            List<Photos> person1  = new ArrayList<>();
                            for (int i = 0; i < currentUser.getAlbums().size(); i++) {
                                for (int j = 0; j < currentUser.getAlbums().get(i).getListofphotos().size(); j++) {
                                    for (int x = 0; x < currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size(); x++) {
                                        if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(locationTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location"))) {
                                            loc.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                        } else if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().startsWith(locationTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("location"))) {
                                            loc.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                        }
                                        //need to implement startswith();
                                    }
                                }
                            }

                            for (int i = 0; i < currentUser.getAlbums().size(); i++) {
                                for (int j = 0; j < currentUser.getAlbums().get(i).getListofphotos().size(); j++) {
                                    for (int x = 0; x < currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().size(); x++) {
                                        if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().equalsIgnoreCase(personTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person"))) {
                                            person1.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                        } else if ((currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getValue().startsWith(personTextView.getText().toString())) && (currentUser.getAlbums().get(i).getListofphotos().get(j).getTags().get(x).getName().equalsIgnoreCase("person"))) {
                                            person1.add(currentUser.getAlbums().get(i).getListofphotos().get(j));
                                        }
                                        //need to implement startswith();
                                    }
                                }
                            }


                            List<Photos> result = new ArrayList<>(person1);
                            result.retainAll(loc);
                            searchList = result;
                            Log.d("RETAINALL",searchList.size()+ " ");


                        }


                    }

                    if(searchList.isEmpty()){
                        AlertDialog.Builder builder = new AlertDialog.Builder(newSearchClass.this);
                        builder.setTitle("Empty Result");
                        builder.setMessage("Invalid Tag");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                        builder.show();
                    }
                    else
                    {
                        Log.d("TOTALUSERS","searchlist" + searchList.size());
                        Log.d("TOTALUSERS1","totalalbum" + currentUser.getAlbums().size());

                        Album searchResultAlbum = new Album("SearchR");
                        searchResultAlbum.setListofphotos(searchList);
                        currentUser.addAlbum(searchResultAlbum);
                        Log.d("TOTALUSERS2","totalalbum" + currentUser.getAlbums().size());
                        albumUsers.saveToDisk(newSearchClass.this);

                        Intent intent = new Intent(newSearchClass.this,searchResults.class);
                        startActivity(intent);




                    }


                }
            });



        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

}

