package com.example.shosho.dietfood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;


import com.dietfoooood.R;
import com.example.shosho.dietfood.SplashActivity;
import com.example.shosho.dietfood.common.Constants;
import com.example.shosho.dietfood.fragment.PackageDetailsFragment;
import com.example.shosho.dietfood.fragment.PostOrderFragment;
import com.example.shosho.dietfood.model.PostOrderData;
import com.example.shosho.dietfood.model.User;
import com.example.shosho.dietfood.presenter.PaidConsultationPresenter;
import com.example.shosho.dietfood.presenter.PostOrderPresenter;
import com.example.shosho.dietfood.presenter.SubscribtionPresenter;
import com.example.shosho.dietfood.task.CheckoutIdRequestAsyncTask;
import com.example.shosho.dietfood.task.CheckoutIdRequestListener;
import com.example.shosho.dietfood.task.PaymentStatusRequestAsyncTask;
import com.example.shosho.dietfood.task.PaymentStatusRequestListener;
import com.example.shosho.dietfood.view.PaidConsultationView;
import com.example.shosho.dietfood.view.PostOrderView;
import com.example.shosho.dietfood.view.SubscribtionView;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;

/**
 * Represents a base activity for making the payments with mobile sdk.
 * This activity handles payment callbacks.
 */
@SuppressLint("Registered")
public class BasePaymentActivity extends BaseActivity
        implements PaidConsultationView, PostOrderView, SubscribtionView, CheckoutIdRequestListener, PaymentStatusRequestListener {

    private static final String STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH";
    PaidConsultationPresenter paidConsultationPresenter;
    SubscribtionPresenter subscribtionPresenter;
    protected String resourcePath;
    PostOrderPresenter postOrderPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postOrderPresenter=new PostOrderPresenter(getBaseContext(),this);
        paidConsultationPresenter=new PaidConsultationPresenter(getBaseContext(),this);
        subscribtionPresenter=new SubscribtionPresenter( getBaseContext(),this );
        if (savedInstanceState != null) {
            resourcePath = savedInstanceState.getString(STATE_RESOURCE_PATH);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        /* Check if the intent contains the callback scheme. */
        if (resourcePath != null && hasCallbackScheme(intent)) {
            requestPaymentStatus(resourcePath);
        }
    }
    /**
     * Returns <code>true</code> if the Intent contains one of the predefined schemes for the app.
     *
     * @param intent the incoming intent
     * @return <code>true</code> if the Intent contains one of the predefined schemes for the app;
     *         <code>false</code> otherwise
     */
    protected boolean hasCallbackScheme(Intent intent) {
        String scheme = intent.getScheme();

        return getString(R.string.checkout_ui_callback_scheme).equals(scheme) ||
                getString(R.string.payment_button_callback_scheme).equals(scheme) ||
                getString(R.string.custom_ui_callback_scheme).equals(scheme);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_RESOURCE_PATH, resourcePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CheckoutActivity.CHECKOUT_ACTIVITY) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    /* Transaction completed. */
                    Transaction transaction = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    resourcePath = data.getStringExtra(
                            CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    /* Check the transaction type. */
                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* Check the status of synchronous transaction. */
                        requestPaymentStatus(resourcePath);
                    } else {
                        /* The on onNewIntent method may be called before onActivityResult
                           if activity was destroyed in the background, so check
                           if the intent already has the callback scheme */
                        if (hasCallbackScheme(getIntent())) {
                            requestPaymentStatus(resourcePath);
                        } else {
                            /* The on onNewIntent method wasn't called yet,
                               wait for the callback. */
                            showProgressDialog(R.string.progress_message_please_wait);
                        }
                    }

                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    hideProgressDialog();

                    break;
                case CheckoutActivity.RESULT_ERROR:
                    PaymentError error = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_ERROR);

                    showAlertDialog(R.string.error_message);
            }
        }
    }

    protected void requestCheckoutId(String callbackScheme) {
        showProgressDialog(R.string.progress_message_checkout_id);

        new CheckoutIdRequestAsyncTask(this)
                .execute(Constants.Config.AMOUNT, Constants.Config.CURRENCY);
    }

    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        hideProgressDialog();

//        if (checkoutId == null) {
//            showAlertDialog(R.string.error_message);
//        }
    }

    @Override
    public void onErrorOccurred() {
        hideProgressDialog();
        showAlertDialog(R.string.error_message);
    }

    @Override
    public void onPaymentStatusReceived(String paymentStatus) {


        if ("000.000.000".equals(paymentStatus)) {
               Order();
        }

        if ("000.000.100".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.100.110".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.100.111".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.100.112".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.300.000".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.300.100".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.300.101".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.300.102".equals(paymentStatus)) {
            Order();
            return;
        }
        if ("000.600.000".equals(paymentStatus)) {
            Order();
            return;
        }


            showAlertDialog(R.string.message_unsuccessful_payment);
    }

    protected void requestPaymentStatus(String resourcePath) {
        showProgressDialog(R.string.progress_message_payment_status);
        new PaymentStatusRequestAsyncTask(this).execute(resourcePath);
    }

    /**
     * Creates the new instance of {@link CheckoutSettings}
     * to instantiate the {@link CheckoutActivity}.
     *
     * @param checkoutId the received checkout id
     * @return the new instance of {@link CheckoutSettings}
     */
    protected CheckoutSettings createCheckoutSettings(String checkoutId, String callbackScheme) {
        return new CheckoutSettings(checkoutId, Constants.Config.PAYMENT_BRANDS,
                Connect.ProviderMode.LIVE)
                .setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS)
                .setWindowSecurityEnabled(false)
                .setShopperResultUrl(callbackScheme + "://callback");
    }

    @Override
    public void showPostOrderResult(PostOrderData postOrderData) {
        hideProgressDialog();
        Intent inty=new Intent(getBaseContext(), RequestedOrderSuccessfully.class);
        inty.putExtra("price",String.valueOf(postOrderData.getTotal()));
        inty.putExtra("id",String.valueOf(postOrderData.getOrderId()));
        startActivity(inty);
      finish();

    }

    @Override
    public void showPostOrderRe(String message) {


    }

    @Override
    public void showPaidConsultationResult(String Msg) {
        hideProgressDialog();
        Intent inty=new Intent(getBaseContext(), RequestedSucess.class);
        inty.putExtra("message","تم ارسال استشارتك بنجاح");
        startActivity(inty);

        finish();

    }

    @Override
    public void showSubscribtionResult(String Message) {
        hideProgressDialog();
        Intent inty=new Intent(getBaseContext(), RequestedSucess.class);
        inty.putExtra("message","تم الاشتراك بالباقة بنجاح");
        startActivity(inty);
        finish();
    }

    @Override
    public void showError() {

    }

    public  void Order(){


        if(PostOrderFragment.Tybe.equals("order")){
            User user=new User();
            user.setUserToken(SplashActivity.Login);
            user.setPhone(PostOrderFragment.Phone);

            postOrderPresenter.getPostOrder(user);
            return;

        }else if(PostOrderFragment.Tybe.equals("eshtrak")){
            subscribtionPresenter.getSubscribtionResult( SplashActivity.Login, PackageDetailsFragment.Id);
            return;
        }
        else if(PostOrderFragment.Tybe.equals("esteshara")){

            User user=new User();
            user.setName(PostOrderFragment.Name);
            user.setPhone(PostOrderFragment.Phone);
            user.setEmail(PostOrderFragment.Email);
            user.setMsg(PostOrderFragment.Msg);
            paidConsultationPresenter.getPaidConsultationResult(user);
            return;

        }

    }
}
