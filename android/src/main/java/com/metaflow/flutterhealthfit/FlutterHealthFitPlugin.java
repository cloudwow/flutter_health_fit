package com.metaflow.flutterhealthfit;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;

import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FlutterHealthFitPlugin
        implements MethodCallHandler,
        PluginRegistry.RequestPermissionsResultListener {
    private Registrar registrar;
    private Result result;

    private FlutterHealthFitPlugin(Registrar registrar) {
        this.registrar = registrar;
    }

    @Override
    public void onMethodCall(MethodCall methodCall, Result result) {
        switch (methodCall.method) {
            case "getPlatformVersion":
                result.success("Android ${android.os.Build.VERSION.RELEASE}");
                break;

            case "getBasicHealthData":
                result.success(new HashMap<String, String>());
                break;
            case "requestAuthorization":
                connect(result);
                break;
            case "getActivity":
                String name = methodCall.argument("name");

                if (name.equals("steps")) {
                    getYesterdaysStepsTotal(result);

                } else {
                    Map<String, Double> map = new HashMap<>();
                    map.put("value", 0.0);
                    result.success(map);
                }
                break;
            default:
                result.notImplemented();
        }
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel =
                new MethodChannel(registrar.messenger(), "flutter_health_fit");
        FlutterHealthFitPlugin simplePermissionsPlugin =
                new FlutterHealthFitPlugin(registrar);
        channel.setMethodCallHandler(simplePermissionsPlugin);
        registrar.addRequestPermissionsResultListener(simplePermissionsPlugin);
    }

    @Override
    public boolean onRequestPermissionsResult(int requestCode,
                                              String[] permissions,
                                              int[] grantResults) {
        return true;
    }

    void connect(Result result) {
    }

    FitnessOptions getFitnessOptions() {
        return FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
                .build();
    }

    void getYesterdaysStepsTotal(Result result) {
    }

    private void recordData(Function<Boolean, Unit> unit:(Boolean) ->Unit)

    {
        Fitness.getRecordingClient(activity,
                GoogleSignIn.getLastSignedInAccount(activity) !!)
                  .subscribe(dataType)
            .addOnSuccessListener {
        callback(true)
    }
                  .addOnFailureListener {
        callback(false)
    }
    }

}