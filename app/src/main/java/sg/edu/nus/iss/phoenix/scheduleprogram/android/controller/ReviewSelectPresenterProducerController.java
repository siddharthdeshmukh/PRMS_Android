package sg.edu.nus.iss.phoenix.scheduleprogram.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrievePresenterDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.delegate.RetrieveProducerDelegate;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ReviewSelectPresenterProducerScreen;
import sg.edu.nus.iss.phoenix.scheduleprogram.android.ui.ScheduleProgramScreen;
import sg.edu.nus.iss.phoenix.user.entity.Presenter;
import sg.edu.nus.iss.phoenix.user.entity.Producer;

/**
 * Created by Shubhanshu Gautam (e0146956) on 9/28/2017.
 */

public class ReviewSelectPresenterProducerController {
    // Tag for logging.
    private static final String TAG = ReviewSelectPresenterProducerController.class.getName();

    private ReviewSelectPresenterProducerScreen reviewSelectPresenterProducerScreen;
    private Presenter presenterSelected=null;
    private Producer producerSelected=null;

    public void startUseCase(String type) {
        // valid types: 'presenter' or 'producer'
        presenterSelected = null;
        producerSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectPresenterProducerScreen.class);
        intent.putExtra("type",type);
        MainController.displayScreen(intent);
    }

    public void startUseCase() {
        presenterSelected = null;
        producerSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectPresenterProducerScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectPresenterProducerScreen reviewSelectPresenterProducerScreen) {
        this.reviewSelectPresenterProducerScreen = reviewSelectPresenterProducerScreen;
        new RetrievePresenterDelegate(this).execute("all");
        new RetrieveProducerDelegate(this).execute("all");
    }

    public void presentersRetrieved(List<Presenter> presenters) {
        reviewSelectPresenterProducerScreen.showPresenters(presenters);
    }
    public void producersRetrieved(List<Producer> producers) {
        reviewSelectPresenterProducerScreen.showProducers(producers);
    }

    public void selectPresenter(Presenter presenter) {
        presenterSelected = presenter;
        Log.v(TAG, "Selected presenter: " + presenter.getName() + ".");
        ControlFactory.getScheduleController().reviewSelectPresenterSelected(presenter);
        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
        intent.putExtra("presenter",presenter.getName());
        MainController.displayScreen(intent);
        //TODO call the base use case controller with the selected presenter.

    }
    public void selectProducer(Producer producer) {
        producerSelected = producer;
        Log.v(TAG, "Selected producer: " + producer.getName() + ".");
        ControlFactory.getScheduleController().reviewSelectProducerSelected(producer);
        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
        intent.putExtra("producer",producer.getName());
        MainController.displayScreen(intent);
        //TODO call the base use case controller with the selected presenter.

    }

    public void selectCancel() {
        presenterSelected = null;
        producerSelected=null;
        Log.v(TAG, "Cancelled the selection of presenter/producer.");
        Intent intent = new Intent(MainController.getApp(), ScheduleProgramScreen.class);
        MainController.displayScreen(intent);
        // TODO call the base use case controller without selection;

    }
}
