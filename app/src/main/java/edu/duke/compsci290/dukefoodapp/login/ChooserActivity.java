package edu.duke.compsci290.dukefoodapp.login;

/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * THIS IS MAIN ACTIVITY. NEXT ITERATION ADD EMAIL PASSWORD ACTIVITY BUTTON
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import edu.duke.compsci290.dukefoodapp.R;

public class ChooserActivity extends AppCompatActivity  {

    private Button button;
    private static final Class[] CLASSES = new Class[]{
            GoogleSignInActivity.class
            // EmailPasswordActivity.class,

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);

        //set up button and onclick
        button = findViewById(R.id.chooseGoogleSignIn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                toGoogleSignIn();
            }
        });

    }

    private void toGoogleSignIn() {
        startActivity(new Intent(this, GoogleSignInActivity.class));
    }
}

