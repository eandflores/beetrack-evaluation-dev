/**
 * Copyright 2015 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beetrack.evaluation.repository.network;

public class Constants {

  public static final String ARTICLES_API     = "https://newsapi.org";
  public static final String SUCCESS_RESPONSE = "ok";

  public static final class Endpoint {
    public static final String GET_ARTICLES   = "/v2/top-headlines?country=us&category=business&apiKey=e8df71c5f5834ca3aeb5bceec66825e9";
  }
}
