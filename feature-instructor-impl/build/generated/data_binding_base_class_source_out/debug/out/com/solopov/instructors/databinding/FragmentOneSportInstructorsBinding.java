// Generated by view binder compiler. Do not edit!
package com.solopov.instructors.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.solopov.instructors.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentOneSportInstructorsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView instructorsRv;

  @NonNull
  public final TextView noInstructorsFoundTv;

  private FragmentOneSportInstructorsBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView instructorsRv, @NonNull TextView noInstructorsFoundTv) {
    this.rootView = rootView;
    this.instructorsRv = instructorsRv;
    this.noInstructorsFoundTv = noInstructorsFoundTv;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentOneSportInstructorsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentOneSportInstructorsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_one_sport_instructors, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentOneSportInstructorsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.instructors_rv;
      RecyclerView instructorsRv = ViewBindings.findChildViewById(rootView, id);
      if (instructorsRv == null) {
        break missingId;
      }

      id = R.id.no_instructors_found_tv;
      TextView noInstructorsFoundTv = ViewBindings.findChildViewById(rootView, id);
      if (noInstructorsFoundTv == null) {
        break missingId;
      }

      return new FragmentOneSportInstructorsBinding((ConstraintLayout) rootView, instructorsRv,
          noInstructorsFoundTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
