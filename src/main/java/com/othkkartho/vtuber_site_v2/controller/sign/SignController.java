package com.othkkartho.vtuber_site_v2.controller.sign;

import com.othkkartho.vtuber_site_v2.controller.response.Response;
import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignInRequest;
import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignUpRequest;
import com.othkkartho.vtuber_site_v2.service.membermanage.SignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.othkkartho.vtuber_site_v2.controller.response.Response.success;

@Api(value = "Sign Controller", tags = "Sign")
@RestController
@RequiredArgsConstructor
public class SignController {
    private final SignService signService;

//    @PostMapping("/api/sign-up")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Response JsonSignUp(@Valid @RequestBody SignUpRequest req) {
//        System.out.println("controller: "+req);
//        signService.signUp(req);
//        return success();
//    }

    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @PostMapping("/api/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public Response FormSignUp(@Valid SignUpRequest req) {
        System.out.println("controller: "+req);
        signService.signUp(req);
        return success();
    }

    @ApiOperation(value = "로그인", notes = "로그인을 한다.")
    @PostMapping("/api/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public Response signIn(@Valid SignInRequest req) {
        return success(signService.signIn(req));
    }

    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰으로 새로운 액세스 토큰을 발급 받는다.")
    @PostMapping("/api/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public Response refreshToken(@RequestHeader(value = "Authorization") String refreshToken) {
        return success(signService.refreshToken(refreshToken));
    }
}
