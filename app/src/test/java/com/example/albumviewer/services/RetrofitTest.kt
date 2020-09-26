package com.example.albumviewer.services

import com.example.albumviewer.model.Album
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.runners.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

/** Test class is used to test Retrofit client
 * @see Retrofit
 * **/
@RunWith(MockitoJUnitRunner::class)
class RetrofitTest {

    private var mMockWebServer: MockWebServer? = null
    private var service : PlaceHolderService? = null

    @Before
    fun setUp() {
        mMockWebServer = MockWebServer()
        mMockWebServer!!.start()
        val retrofit: Retrofit =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(
                    mMockWebServer!!.url("").toUrl()
                ) //TODO Add your Retrofit parameters here
                .build()
        //Set a response for retrofit to handle. You can copy a sample
        //response from your server to simulate a correct result or an error.
        //MockResponse can also be customized with different parameters
        //to match your test needs

        //Set a response for retrofit to handle. You can copy a sample
        //response from your server to simulate a correct result or an error.
        //MockResponse can also be customized with different parameters
        //to match your test needs
        service= retrofit.create(PlaceHolderService::class.java)
    }

    @After
    fun tearDown() {
        mMockWebServer!!.shutdown()
    }

    @Test
    fun test() {

        val list: MutableList<Album> = ArrayList()
        for (x in 0 until 10) {
            val album = Album(x, x, "Album".plus(x))
            list.add(album)
        }
        val myGson: Gson = GsonBuilder().create()
        val jsonString = myGson.toJson(list)
        mMockWebServer!!.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setHttp2ErrorCode(500)
                .setBodyDelay(1000, TimeUnit.MILLISECONDS)
                .setBody(jsonString)
        )
        val list1: MutableList<Album> = ArrayList()
        for (x in 0 until 10) {
            val album = Album(x, x, "Album 1 -".plus(x))
            list1.add(album)
        }
        val jsonBody = myGson.toJson(list1)
        mMockWebServer!!.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setHttp2ErrorCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                .setBodyDelay(2000, TimeUnit.MILLISECONDS)
                .setBody(jsonBody)
        )

        //With your service created you can now call its method that should
        //consume the MockResponse above. You can then use the desired
        //assertion to check if the result is as expected. For example:
        val call: Call<List<Album?>?> = service!!.listAlbums()
        val response = call.execute()
        assertNotNull(response)
        assertEquals(HttpURLConnection.HTTP_OK, response.code())
        assertTrue(response.isSuccessful)
        assertEquals(response.body(), list)
        assertEquals(1, mMockWebServer!!.requestCount)

        val call1: Call<List<Album?>?> = service!!.listAlbums()
        val response1 = call1.execute()
        assertNotNull(response1)
        assertEquals(HttpURLConnection.HTTP_OK, response1.code())
        assertTrue(response1.isSuccessful)
        assertEquals(response1.body(), list1)
        assertEquals(2, mMockWebServer!!.requestCount)

    }

}