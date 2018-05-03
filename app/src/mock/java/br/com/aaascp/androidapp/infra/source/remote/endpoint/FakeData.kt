package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.MovieResponse
import br.com.aaascp.androidapp.util.AndroidUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FakeData {

    fun getAreas(start: Int, end: Int): MovieResponse? {
        val responseType = object : TypeToken<MovieResponse>() {}.type
        val response: MovieResponse? =
                Gson().fromJson(
                        readFile("areas"),
                        responseType)

        response?.data?.let {
            val safeEnd = if (it.size < end) it.size else end
            response.data = it.subList(start, safeEnd)
        }

        return response
    }

    private fun readFile(fileName: String): String? {
        return AndroidUtils()
                .getAssetFile("$fileName.json")
                ?.bufferedReader()
                .use {
                    it?.readText()
                }
    }
}

