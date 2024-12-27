package com.example.navigationdrawer.ui.home;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationCompat;

import com.example.navigationdrawer.R;

public class promodo extends Fragment {
    private TextView timerLabel;
    private EditText focusTimeInput, breakTimeInput, totalFocusTimeInput;
    private Button startButton, pauseButton, resetButton;

    private CountDownTimer countDownTimer;
    private long remainingTimeInMillis;
    private boolean isTimerRunning = false;
    private boolean isFocusPhase = true;
    private long totalSessionTimeInMillis;
    private long focusTimeInMillis;
    private long breakTimeInMillis;

    private static final String CHANNEL_ID = "FocusAppChannel";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.promodo, container, false);

        // Initialize UI components
        timerLabel = rootView.findViewById(R.id.timerLabel);
        focusTimeInput = rootView.findViewById(R.id.focusTimeInput);
        breakTimeInput = rootView.findViewById(R.id.breakTimeInput);
        totalFocusTimeInput = rootView.findViewById(R.id.totalFocusTimeInput);
        startButton = rootView.findViewById(R.id.startButton);
        pauseButton = rootView.findViewById(R.id.pauseButton);
        resetButton = rootView.findViewById(R.id.resetButton);

        // Set up button listeners
        startButton.setOnClickListener(v -> startFocusSession());
        pauseButton.setOnClickListener(v -> pauseFocusSession());
        resetButton.setOnClickListener(v -> resetFocusSession());

        createNotificationChannel();
        return rootView;
    }

    private void startFocusSession() {
        if (!isTimerRunning) {
            try {
                int focusMinutes = Integer.parseInt(focusTimeInput.getText().toString());
                int breakMinutes = Integer.parseInt(breakTimeInput.getText().toString());
                int totalMinutes = Integer.parseInt(totalFocusTimeInput.getText().toString());

                if (focusMinutes <= 0 || breakMinutes <= 0 || totalMinutes <= 0) {
                    showToast("Please enter positive values for all fields.");
                    return;
                }

                focusTimeInMillis = focusMinutes * 60 * 1000;
                breakTimeInMillis = breakMinutes * 60 * 1000;
                totalSessionTimeInMillis = totalMinutes * 60 * 1000;

                isFocusPhase = true;
                startTimer(focusTimeInMillis);
            } catch (NumberFormatException e) {
                showToast("Please enter valid numbers for time.");
            }
        }
    }

    private void startTimer(long durationInMillis) {
        countDownTimer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeInMillis = millisUntilFinished;
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if (isFocusPhase) {
                    sendNotification("Break Started", "Time for a short break!");
                    startTimer(breakTimeInMillis);
                } else {
                    sendNotification("Focus Started", "Back to work!");
                    startTimer(focusTimeInMillis);
                }
                isFocusPhase = !isFocusPhase;
            }
        }.start();

        isTimerRunning = true;
    }

    private void pauseFocusSession() {
        if (countDownTimer != null && isTimerRunning) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }
    }

    private void resetFocusSession() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isTimerRunning = false;
        remainingTimeInMillis = 0;
        updateTimerDisplay(0);
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void showToast(String message) {
        // Use getActivity() context for Toast in Fragment
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NotificationPermission")
    private void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Focus Notifications";
            String description = "Channel for FocusApp notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
