package com.example.exam_addressbook;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<TextView>     addressList;
    private EditText                editName;
    private EditText                editPhone;
    private EditText                editEmail;
//    private TextView                prevTextView;
    private LinearLayout            showAddressListLayout;
    private int                     clickBtnId;


    // 주소록 저장 양식
    public class AddressBook {
        String name;
        String phone;
        String email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initApp();
    }

    // -------------------------
    // -- 앱 시작 시 값 할당 ----
    // -------------------------
    public void initApp() {
        addressList =               new ArrayList<TextView>();
        showAddressListLayout =     findViewById(R.id.address_list);
        editName =                  findViewById(R.id.edit_Name);
        editPhone =                 findViewById(R.id.edit_Phone);
        editEmail =                 findViewById(R.id.edit_Email);
        clickBtnId =                -1;
    }

    // -------------------------------------
    // -- 버튼 클릭 시 해당 버튼 기능 수행 -----
    // -------------------------------------
    public void clickFunc(View v) {
        switch (v.getId()) {
            case R.id.btn_Add:
                add_Address();
                break;
            case R.id.btn_Delete:
                delete_Address();
                break;
            case R.id.btn_Show:
                show_Address();
                break;
            default:
                Toast.makeText(this.getApplicationContext(), R.string.msg_error, Toast.LENGTH_LONG).show();
        }

    }

    // -------------------------
    // -- 주소록에 주소 추가 -----
    // -------------------------
    public void add_Address() {
        if (editName.getText().toString().isEmpty()
                || editPhone.getText().toString().isEmpty()
                || editEmail.getText().toString().isEmpty()) {
            Toast.makeText(this.getApplicationContext(), R.string.msg_noValue, Toast.LENGTH_LONG).show();
        } else {
            addressList.add(create_newAddress()); // 목록에서 볼 TextView를 ArrayList에 저장 완료
            Log.i(String.valueOf(addressList.size()), "추가 완료");
            Toast.makeText(this.getApplicationContext(), R.string.msg_successSave, Toast.LENGTH_LONG).show();
            clear_EditText();
        }
    }

    // --------------------------------------------------
    // ---- 이름, 휴대폰번호, 이메일을 입력해서 객체에 저장 ----
    // --------------------------------------------------
    public TextView create_newAddress() {
        AddressBook newAddress =    new AddressBook();
        newAddress.name =           editName.getText().toString();
        newAddress.phone =          editPhone.getText().toString();
        newAddress.email =          editEmail.getText().toString();
        return make_TextView(newAddress);
    }

    // ------------------------------------------------
    // -- 주소록 텍스트뷰 만든 후 목록 레이아웃에 추가 -------
    // ------------------------------------------------
    public TextView make_TextView(AddressBook newAddress) {
        TextView address;
        address = new TextView(this);
        address.setText(get_AddressText(newAddress));
        address.setId(addressList.size());
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isSame = clickBtnId;
                clickBtnId = address.getId();
                if(isSame == clickBtnId)
                    address.setBackgroundColor(Color.WHITE);
                else {
//                    prevTextView = address;
//                    prevTextView.setBackgroundColor(Color.WHITE);
                    address.setBackgroundColor(Color.YELLOW);
                }

                Log.i(String.valueOf(address.getId()), "클릭했음");
            }
        });
        // prevTextView = address;
        // set_addressToList(address);
        return address;
    }

    // -------------------------------------------------------
    // -- 리스트 레이아웃에 추가하기 위한 주소 문자열 생성 ----------
    // -------------------------------------------------------
    public String get_AddressText(AddressBook i) {
        return "이름 : " + i.name + ", " + "전화번호 : " + i.phone + ", " + "이메일 주소 : " + i.email;
    }

    // ------------------------------------------------------
    // ---- 이름, 휴대폰번호, 이메일 EditText 글자 모두 지우기 ----
    // ------------------------------------------------------
    public void clear_EditText() {
        editName.setText("");
        editPhone.setText("");
        editEmail.setText("");
    }

    // -------------------------
    // -- 주소록에서 주소 삭제 -----
    // -------------------------
    public void delete_Address() {
        if(addressList.size() == 0)
            return;
        modify_ViewId();
        addressList.remove(clickBtnId);
        Toast.makeText(this.getApplicationContext(), R.string.msg_successDelete, Toast.LENGTH_LONG).show();
        clickBtnId = -1;
        show_Address();
    }


    // ----------------------------------------
    // -- 삭제로 인한 텍스트뷰의 아이디 값 조정 -----
    // ----------------------------------------
    public void modify_ViewId() {
//        if(addressList.size() == 1)
//            return;
        for(int i=clickBtnId+1; i<addressList.size(); i++) {
            int id = addressList.get(i).getId();
            addressList.get(i).setId(id-1);
        }
    }

    // -------------------------
    // -- 주소록 목록 출력 -------
    // -------------------------
    public void show_Address() {
        showAddressListLayout.removeAllViews();
        if (addressList.size() == 0) {
            Log.i(String.valueOf(addressList.size()), "저장된 주소가 없음");
            return;
            // Toast.makeText(this.getApplicationContext(), "주소록이 비었습니다.", Toast.LENGTH_LONG); 안 나옴;
        }
        for (TextView i : addressList) {
            set_addressToList(i);
        }
    }

    // -------------------------------------------------------
    // -- 만든 주소를 주소 리스트 레이아웃에 추가해서 출력 ----------
    // -------------------------------------------------------
    public void set_addressToList(TextView address) {
        Log.i("set_addressToList", "결과 출력");
        showAddressListLayout.addView(address);
    }
}