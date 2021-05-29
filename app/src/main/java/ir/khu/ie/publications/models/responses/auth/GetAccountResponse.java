package ir.khu.ie.publications.models.responses.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.khu.ie.publications.models.responses.Response;

public class GetAccountResponse extends Response {

    @SerializedName("data")
    @Expose
    private final Data data;

    public GetAccountResponse(String status, String message, Data data) {
        super(status, message);
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("phone")
        @Expose
        private final String phone;
        @SerializedName("user_name")
        @Expose
        private final String userName;
        @SerializedName("jwt")
        @Expose
        private final String jwt;
        @SerializedName("access_token")
        @Expose
        private final String accessToken;

        public Data(String phone, String userName, String jwt, String accessToken) {
            this.phone = phone;
            this.userName = userName;
            this.jwt = jwt;
            this.accessToken = accessToken;
        }

        public String getPhone() {
            return phone;
        }

        public String getUserName() {
            return userName;
        }

        public String getJwt() {
            return jwt;
        }

        public String getAccessToken() {
            return accessToken;
        }
    }
}
