package com.akimi808.bookmateclient;

import java.util.List;

import com.akimi808.bookmateclient.model.Book;
import com.akimi808.bookmateclient.model.Quote;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author Andrey Larionov
 * Библиотека Retrofit во время работы программы самасоздаст класс, который реализует этот интерфейс
 */
public interface Bookmate {
    @GET("/a/4/u/{user}/m.json")
    Call<List<Quote>> quotes(@Path("user") String username, @Query("only_public") Boolean onlyPublic,
                             @Query("p") Integer page, @Query("pp") Integer perPage);

    @GET("/a/4/d/{uuid}.json")
    Call<Book> book(@Path("uuid") String bookUuid);
}
