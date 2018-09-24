package br.com.aaascp.androidapp.infra.repository;

import java.util.List;

public abstract class RepositoryCallback<T> {

    public void onSuccess(T data) {
    }

    public void onError(List<String> errors) {
    }
}
