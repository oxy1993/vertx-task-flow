package com.oxy.vertx.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorDTO {
    @JsonProperty("id")
    private int id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("added")
    private String added;

    public AuthorDTO() {
    }

    private AuthorDTO(int id, String firstName, String lastName, String email, String birthdate, String added) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.added = added;
    }

    // Builder
    private static abstract class AuthorBuilderBase<T extends AuthorBuilderBase<T, S>, S extends AuthorDTO> {
        protected S authorDTO;
        public S build() {
            return authorDTO;
        }
    }

    public static class Builder extends AuthorBuilderBase<Builder, AuthorDTO> {
        public Builder() {
            authorDTO = new AuthorDTO();
        }

        public Builder setId(int id) {
            this.authorDTO.id = id;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.authorDTO.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.authorDTO.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.authorDTO.email = email;
            return this;
        }

        public Builder setBirthdate(String birthdate) {
            this.authorDTO.birthdate = birthdate;
            return this;
        }

        public Builder setAdded(String added) {
            this.authorDTO.added = added;
            return this;
        }
     }

    public static AuthorFluentBuilder AuthorFluentBuilder() {
        return id -> firstName -> lastName -> email -> birthdate -> added -> () -> new AuthorDTO(id, firstName, lastName, email, birthdate, added);
    }

    public interface AuthorFluentBuilder {
        FirstNameStep setId(int id);
    }

    public interface FirstNameStep {
        LastNameStep setFirstName(final String firstName);
    }

    public interface LastNameStep {
        EmailStep setLastName(final String lastName);
    }

    public interface EmailStep {
        BirthdateStep setEmail(final String email);
    }

    public interface BirthdateStep {
        AddedStep setBirthdate(final String birthdate);
    }

    public interface AddedStep {
        BuildStep setAdded(final String added);
    }

    public interface BuildStep {
        AuthorDTO build();
    }
}
