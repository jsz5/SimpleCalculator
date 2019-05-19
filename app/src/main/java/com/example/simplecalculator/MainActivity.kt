package com.example.simplecalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var newton: NewtonAPI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newton.now.sh")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newton = retrofit.create(NewtonAPI::class.java)
        listener(simplify,"simplify")
        listener(factor, "factor")
        listener(derive,"derive")
        listener(integrate, "integrate")
        listener(findTangent,"tangent")
        listener(area,"area")
        listener(cosine,"cos")
        listener(sine,"sin")
        listener(tangent,"tan")
        listener(inverseCosine,"arccos")
        listener(inverseSine,"arcsin")
        listener(inverseTangent,"arctan")
        listener(abs,"abs")
        listener(log,"log")
        zeros.setOnClickListener {
            val expressionInput=findViewById<EditText>(R.id.expression).text.toString()
            if(checkExpression(expressionInput)){
              val call=newton.zeroes(expressionInput)
                call.enqueue(object : Callback<FindZeroesData> {
                    override fun onFailure(call: Call<FindZeroesData>, t: Throwable) {
                        Log.d("Failure", "Failure")
                    }
                    override fun onResponse(call: Call<FindZeroesData>, response: Response<FindZeroesData>) {
                        val body = response.body()
                        resultInput.text="["+body!!.result[0]+" ,"+body!!.result[1]+"]"
                    }
                })
            }
        }


    }
    private fun listener(button: Button, operation: String){
        button.setOnClickListener {
            val expressionInput=findViewById<EditText>(R.id.expression).text.toString()
            if(checkExpression(expressionInput)){
                loadResult(newton.function(operation,expressionInput))
            }
        }
    }
    private fun loadResult(call: Call<NewtonData>){
        call.enqueue(object : Callback<NewtonData> {
            override fun onFailure(call: Call<NewtonData>, t: Throwable) {
                Log.d("Failure", "Failure")
            }
            override fun onResponse(call: Call<NewtonData>, response: Response<NewtonData>) {
                val body = response.body()
                resultInput.text=body!!.result
            }
        })
    }
    private fun checkExpression(expressionInput: String):Boolean{
        if (expressionInput.isEmpty()) {
            Toast.makeText(this, R.string.emptyExpression, Toast.LENGTH_SHORT).show()
            return false;
        }
        return true
    }
}
