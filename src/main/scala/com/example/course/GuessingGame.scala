package com.example.course

import scala.annotation.tailrec

object GuessingGame {
  def evaluate(secretWord: String, guess: String): Result = {
    @tailrec
    def internal(secret: String, remainingGuess: String, acc: Result, unmatchedSecret: String): Result = {
      if (secret.isEmpty)
        acc
      else {
        val result =
          if (secret.head == remainingGuess.head)
            Result(rightPositions = acc.rightPositions + 1, acc.wrongPositions)
          else {
            val wrongPositions = if (unmatchedSecret.contains(remainingGuess.head)) acc.wrongPositions + 1 else acc.wrongPositions
            Result(acc.rightPositions,
              wrongPositions)
          }

        internal(secret.tail, remainingGuess.tail, result, removeChar(unmatchedSecret, remainingGuess.head))
      }
    }

    internal(secretWord, guess, Result(), secretWord)
  }


  def removeChar(word: String, char: Char): String = {
    @tailrec
    def internal(currentWord: String, acc: String): String = {
      if (currentWord.isEmpty) {
        acc
      } else {
        if (currentWord.head == char)
          acc ++ currentWord.tail
        else
          internal(currentWord.tail, acc ++ currentWord.head.toString)
      }
    }

    internal(word, "")
  }

}


case class Result(rightPositions: Int = 0, wrongPositions: Int = 0)
