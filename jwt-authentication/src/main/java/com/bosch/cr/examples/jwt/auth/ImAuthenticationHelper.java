/*
 * Bosch SI Example Code License Version 1.0, January 2016
 *
 * Copyright 2016 Bosch Software Innovations GmbH ("Bosch SI"). All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 * disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 * following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * BOSCH SI PROVIDES THE PROGRAM "AS IS" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE ENTIRE RISK AS TO THE
 * QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD THE PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL
 * NECESSARY SERVICING, REPAIR OR CORRECTION. THIS SHALL NOT APPLY TO MATERIAL DEFECTS AND DEFECTS OF TITLE WHICH BOSCH
 * SI HAS FRAUDULENTLY CONCEALED. APART FROM THE CASES STIPULATED ABOVE, BOSCH SI SHALL BE LIABLE WITHOUT LIMITATION FOR
 * INTENT OR GROSS NEGLIGENCE, FOR INJURIES TO LIFE, BODY OR HEALTH AND ACCORDING TO THE PROVISIONS OF THE GERMAN
 * PRODUCT LIABILITY ACT (PRODUKTHAFTUNGSGESETZ). THE SCOPE OF A GUARANTEE GRANTED BY BOSCH SI SHALL REMAIN UNAFFECTED
 * BY LIMITATIONS OF LIABILITY. IN ALL OTHER CASES, LIABILITY OF BOSCH SI IS EXCLUDED. THESE LIMITATIONS OF LIABILITY
 * ALSO APPLY IN REGARD TO THE FAULT OF VICARIOUS AGENTS OF BOSCH SI AND THE PERSONAL LIABILITY OF BOSCH SI'S EMPLOYEES,
 * REPRESENTATIVES AND ORGANS.
 */
package com.bosch.cr.examples.jwt.auth;

import com.bosch.im.api2.Scope;
import com.bosch.im.api2.client.IClient;
import com.bosch.im.api2.dto.AuthenticationDto;
import com.bosch.im.api2.dto.AuthorizationDto;

/**
 * Helper class for requesting JSON Web Tokens at Bosch IoT Permissions.
 */
final class ImAuthenticationHelper
{

   private final IClient client;

   /**
    * Constructor.
    *
    * @param client the API 2 client
    */
   public ImAuthenticationHelper(final IClient client)
   {
      this.client = client;
   }

   /**
    * Authenticate using the specified credentials.
    *
    * @return a DTO holding authentication info (jwt)
    */
   public AuthenticationDto authenticate(final String tenantIdOrName, final String userName, final String password)
   {
      return client.authenticate() //
         .userTenantIdOrName(tenantIdOrName) //
         .userName(userName) //
         .password(password) //
         .executeAndGet();
   }

   /**
    * Authorize using the specified authentication info.
    * <p>
    * Note: the authorization token is scoped to contain only permissions and roles of IM3.
    * </p>
    *
    * @param authenticationDto a DTO holding authentication info (jwt)
    * @return a DTO holding authorization info (jwt)
    */
   public AuthorizationDto authorize(final AuthenticationDto authenticationDto)
   {
      return client.authorize() //
         .userTenantId(authenticationDto.getUserTid()) //
         .scopes(Scope.trid, Scope.gid) //
         .idToken(authenticationDto.getIdToken()) //
         .executeAndGet();
   }

}