// Generated by view binder compiler. Do not edit!
package com.solopov.feature_authentication_impl.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.solopov.common.base.view.ProgressButton;
import com.solopov.feature_authentication_impl.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentSignUpBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final TextInputEditText ageEt;

  @NonNull
  public final TextInputLayout ageTextInput;

  @NonNull
  public final TextView ageTv;

  @NonNull
  public final AppCompatImageButton backBtn;

  @NonNull
  public final TextView createAccountTv;

  @NonNull
  public final TextInputEditText emailEt;

  @NonNull
  public final TextInputLayout emailTextInput;

  @NonNull
  public final TextView emailTv;

  @NonNull
  public final RadioButton femaleRb;

  @NonNull
  public final ProgressButton finishSignUpBtn;

  @NonNull
  public final RadioGroup genderRg;

  @NonNull
  public final TextView genderTv;

  @NonNull
  public final RadioButton maleRb;

  @NonNull
  public final TextInputEditText nameEt;

  @NonNull
  public final TextInputLayout nameTextInput;

  @NonNull
  public final TextView nameTv;

  @NonNull
  public final TextInputEditText passwordEt;

  @NonNull
  public final TextInputLayout passwordTextInput;

  @NonNull
  public final TextView passwordTv;

  @NonNull
  public final TextView signUpTv;

  private FragmentSignUpBinding(@NonNull ScrollView rootView, @NonNull TextInputEditText ageEt,
      @NonNull TextInputLayout ageTextInput, @NonNull TextView ageTv,
      @NonNull AppCompatImageButton backBtn, @NonNull TextView createAccountTv,
      @NonNull TextInputEditText emailEt, @NonNull TextInputLayout emailTextInput,
      @NonNull TextView emailTv, @NonNull RadioButton femaleRb,
      @NonNull ProgressButton finishSignUpBtn, @NonNull RadioGroup genderRg,
      @NonNull TextView genderTv, @NonNull RadioButton maleRb, @NonNull TextInputEditText nameEt,
      @NonNull TextInputLayout nameTextInput, @NonNull TextView nameTv,
      @NonNull TextInputEditText passwordEt, @NonNull TextInputLayout passwordTextInput,
      @NonNull TextView passwordTv, @NonNull TextView signUpTv) {
    this.rootView = rootView;
    this.ageEt = ageEt;
    this.ageTextInput = ageTextInput;
    this.ageTv = ageTv;
    this.backBtn = backBtn;
    this.createAccountTv = createAccountTv;
    this.emailEt = emailEt;
    this.emailTextInput = emailTextInput;
    this.emailTv = emailTv;
    this.femaleRb = femaleRb;
    this.finishSignUpBtn = finishSignUpBtn;
    this.genderRg = genderRg;
    this.genderTv = genderTv;
    this.maleRb = maleRb;
    this.nameEt = nameEt;
    this.nameTextInput = nameTextInput;
    this.nameTv = nameTv;
    this.passwordEt = passwordEt;
    this.passwordTextInput = passwordTextInput;
    this.passwordTv = passwordTv;
    this.signUpTv = signUpTv;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSignUpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_sign_up, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSignUpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.age_et;
      TextInputEditText ageEt = ViewBindings.findChildViewById(rootView, id);
      if (ageEt == null) {
        break missingId;
      }

      id = R.id.age_text_input;
      TextInputLayout ageTextInput = ViewBindings.findChildViewById(rootView, id);
      if (ageTextInput == null) {
        break missingId;
      }

      id = R.id.age_tv;
      TextView ageTv = ViewBindings.findChildViewById(rootView, id);
      if (ageTv == null) {
        break missingId;
      }

      id = R.id.back_btn;
      AppCompatImageButton backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.create_account_tv;
      TextView createAccountTv = ViewBindings.findChildViewById(rootView, id);
      if (createAccountTv == null) {
        break missingId;
      }

      id = R.id.email_et;
      TextInputEditText emailEt = ViewBindings.findChildViewById(rootView, id);
      if (emailEt == null) {
        break missingId;
      }

      id = R.id.email_text_input;
      TextInputLayout emailTextInput = ViewBindings.findChildViewById(rootView, id);
      if (emailTextInput == null) {
        break missingId;
      }

      id = R.id.email_tv;
      TextView emailTv = ViewBindings.findChildViewById(rootView, id);
      if (emailTv == null) {
        break missingId;
      }

      id = R.id.female_rb;
      RadioButton femaleRb = ViewBindings.findChildViewById(rootView, id);
      if (femaleRb == null) {
        break missingId;
      }

      id = R.id.finish_sign_up_btn;
      ProgressButton finishSignUpBtn = ViewBindings.findChildViewById(rootView, id);
      if (finishSignUpBtn == null) {
        break missingId;
      }

      id = R.id.gender_rg;
      RadioGroup genderRg = ViewBindings.findChildViewById(rootView, id);
      if (genderRg == null) {
        break missingId;
      }

      id = R.id.gender_tv;
      TextView genderTv = ViewBindings.findChildViewById(rootView, id);
      if (genderTv == null) {
        break missingId;
      }

      id = R.id.male_rb;
      RadioButton maleRb = ViewBindings.findChildViewById(rootView, id);
      if (maleRb == null) {
        break missingId;
      }

      id = R.id.name_et;
      TextInputEditText nameEt = ViewBindings.findChildViewById(rootView, id);
      if (nameEt == null) {
        break missingId;
      }

      id = R.id.name_text_input;
      TextInputLayout nameTextInput = ViewBindings.findChildViewById(rootView, id);
      if (nameTextInput == null) {
        break missingId;
      }

      id = R.id.name_tv;
      TextView nameTv = ViewBindings.findChildViewById(rootView, id);
      if (nameTv == null) {
        break missingId;
      }

      id = R.id.password_et;
      TextInputEditText passwordEt = ViewBindings.findChildViewById(rootView, id);
      if (passwordEt == null) {
        break missingId;
      }

      id = R.id.password_text_input;
      TextInputLayout passwordTextInput = ViewBindings.findChildViewById(rootView, id);
      if (passwordTextInput == null) {
        break missingId;
      }

      id = R.id.password_tv;
      TextView passwordTv = ViewBindings.findChildViewById(rootView, id);
      if (passwordTv == null) {
        break missingId;
      }

      id = R.id.sign_up_tv;
      TextView signUpTv = ViewBindings.findChildViewById(rootView, id);
      if (signUpTv == null) {
        break missingId;
      }

      return new FragmentSignUpBinding((ScrollView) rootView, ageEt, ageTextInput, ageTv, backBtn,
          createAccountTv, emailEt, emailTextInput, emailTv, femaleRb, finishSignUpBtn, genderRg,
          genderTv, maleRb, nameEt, nameTextInput, nameTv, passwordEt, passwordTextInput,
          passwordTv, signUpTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
