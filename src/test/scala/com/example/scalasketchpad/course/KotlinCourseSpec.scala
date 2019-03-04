package com.example.scalasketchpad.course

import com.example.course.{GuessingGame, Result}
import org.scalatest.{FunSpec, Matchers}

class KotlinCourseSpec extends FunSpec with Matchers {
  describe("Kotlin Course") {
    it("should give counts of right and wrong guesses") {
      GuessingGame.evaluate("abcd", "blah") should be(Result(0, 2))
    }

    it("should give counts of right and wrong guesses when they match") {
      GuessingGame.evaluate("abcd", "abcd") should be(Result(4, 0))
    }

    it("should give counts when guess contains same occurrence of a") {
      GuessingGame.evaluate("abcd", "aaeg") should be(Result(1, 0))
    }

    it("should give counts when guess does not contain any matching characters") {
      GuessingGame.evaluate("abcd", "xyzl") should be(Result(0, 0))
    }

    it("should give counts when all chars are in wrong positions") {
      GuessingGame.evaluate("abcd", "badc") should be(Result(0, 4))
    }

    it("should remove first occurrence of a") {
      GuessingGame.removeChar("abcd", 'b') should be("acd")
    }

    it("should remove only one occurrence of a") {
      GuessingGame.removeChar("aabc", 'a') should be("abc")
    }
  }
}
