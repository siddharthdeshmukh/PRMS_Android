package sg.edu.nus.iss.phoenix.scheduleprogram.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 * Created by Shubhanshu Gautam (e0146956) on 9/28/2017.
 */

public class ReviewSelectPresenterProducerScreen extends AppCompatActivity {

    private static final String TAG = SchdeuleListScreen.class.getName();
    private ListView mPresenterListView;
    private ListView mProducerListView;

    private PresenterArrayAdapter mPresenterAdapter ;
    private ProducerArrayAdapter mProducerAdapter ;

    private Presenter mSelectedPresenter = null;
    private Producer mSelectedProducer = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_select_program);

        setupPresenter();
        setupProducer();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getReviewSelectPresenterProducerController().onDisplay(this);
    }

    private void setupPresenter(){
        ArrayList<Presenter> presenters = new ArrayList<Presenter>();
        mPresenterAdapter = new PresenterArrayAdapter(this, presenters);

        mPresenterListView = (ListView) findViewById(R.id.presenter_list);
        mPresenterListView.setAdapter(mPresenterAdapter);

        // Setup the item selection listener
        mPresenterListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mSelectedPresenter = (Presenter) adapterView.getItemAtPosition(position);
                ControlFactory.getReviewSelectPresenterProducerController().selectPresenter(mSelectedPresenter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    private void setupProducer(){

        ArrayList<Producer> producers = new ArrayList<Producer>();
        mProducerAdapter = new ProducerArrayAdapter(this, producers);

        mProducerListView = (ListView) findViewById(R.id.producer_list);
        mProducerListView.setAdapter(mProducerAdapter);

        // Setup the item selection listener
        mProducerListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                mSelectedProducer = (Producer) adapterView.getItemAtPosition(position);
                ControlFactory.getReviewSelectPresenterProducerController().selectProducer(mSelectedProducer);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    @Override
    public void onBackPressed() {
        ControlFactory.getReviewSelectPresenterProducerController().selectCancel();
    }

    public void showPresenters(List<Presenter> presenters) {
        mPresenterAdapter.clear();
        for (int i = 0; i < presenters.size(); i++) {
            mPresenterAdapter.add(presenters.get(i));
        }
    }

    public void showProducers(List<Producer> producers) {
        mProducerAdapter.clear();
        for (int i = 0; i < producers.size(); i++) {
            mProducerAdapter.add(producers.get(i));
        }
    }
}
