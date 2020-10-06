# Contributing to Ethopedia

Want to contribute to the project? This document outlines how to do it.

## Which repository should I contribute to?

Ethopedia has two main repositories:

0. [The site](https://github.com/ethopedia/site)
0. [The graphql api](https://github.com/ethopedia/api) (The one you're in right now)

If you are interested in contributing to the site, you can follow the [contributing guidelines](https://github.com/ethopedia/site/CONTRIBUTING.md) in that repository.

If you are interested in contributing to the api, read on.

## Table of Contents

0. [How to contribute](#how-to-contribute)
0. [Style guide](#style-guide)
0. [Setting up your environment](#setting-up-your-environment)

## How to contribute

If you'd like to contribute, start by searching through the [issues](https://github.com/ethopedia/api/issues) and [pull requests](https://github.com/ethopedia/api/pulls) to see whether someone else has raised a similar idea or question.

If you don't see your idea listed, and you think it fits into the goals of this project, do one of the following:
* **If your contribution is minor,** such as a typo fix, open a pull request.
* **If your contribution is major,** such as an adding a new endpoint or modifying existing behavior, start by opening an issue first. That way, other people can weigh in on the discussion before you do any work.


## Style guide

This project uses [Kotlin](https://kotlinlang.org/). Please try to adhere to the Kotlin coding conventions outlined [here](https://kotlinlang.org/docs/reference/coding-conventions.html). 

The [Guice](https://github.com/google/guice) library is used to manage dependencies. Please try to take advantage of it where appropriate.

*While writing clean code is important, it's better to have messy code that works than nothing! So don't stress too much over it :)*


## Setting up your environment

The api is written in kotlin, so you'll need Java to be installed on your machine. It is recommended to use [IntelliJ IDEA](https://www.jetbrains.com/idea/) since
you can take advantage of the predefined run/build configurations. 

Once you have that set up, you are ready to run the api.

If you are developing a new endpoint that doesn't require the database then select the `RunNoDatabase` run configuration and click `Run`:

![RunNoDatabase Configuration](https://www.ethopedia.org/resources/run-no-database-configuration-min.png)

*Note: The `RunNoDatabase` configuration is a temporary workaround for not having a good way to interface with a local database instance!*

The api should now be running at http://localhost:7250. The graphql endpoint is at http://localhost:7250/graphql.
