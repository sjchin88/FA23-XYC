package main

import (
	// "encoding/json"
	"net/http"
	"fmt"

	"github.com/gin-gonic/gin"
)

type AlbumInfo struct {
	Artist string `json:"artist"`
	Title  string `json:"title"`
	Year   string `json:"year"`
}

type ErrorMsg struct {
	Message string `json:"message"`
}

type ImageMetaData struct {
	AlbumID   string `json:"albumID"`
	ImageSize string `json:"imageSize"`
}

func main() {
	router := gin.Default()

	router.GET("/albums/:albumID", handleGetAlbum)
	router.POST("/albums", handlePostAlbum)

	router.Run(":3000") // Run on port 3000
}

func handleGetAlbum(c *gin.Context) {
	albumID := c.Param("albumID")

	if !isValidAlbumID(albumID) {
		c.JSON(http.StatusBadRequest, ErrorMsg{"Invalid request - invalid albumID"})
		return
	}

	// Sample album data based on API schema (hardcoded for demonstration purposes)
	if albumID == "1" {
		albumInfo := AlbumInfo{
			Artist: "Sex Pistols",
			Title:  "Never Mind The Bollocks!",
			Year:   "1977",
		}
		c.JSON(http.StatusOK, albumInfo)
	} else {
		c.JSON(http.StatusNotFound, ErrorMsg{"Key not found"})
	}
}

func handlePostAlbum(c *gin.Context) {
	var requestData map[string]interface{}
	if err := c.ShouldBindJSON(&requestData); err != nil {
		c.JSON(http.StatusBadRequest, ErrorMsg{"Error processing JSON request"})
		return
	}

	profile, ok := requestData["profile"].(map[string]interface{})
	if !ok {
		c.JSON(http.StatusBadRequest, ErrorMsg{"Invalid request - missing profile"})
		return
	}

	artist, aok := profile["artist"].(string)
	title, tok := profile["title"].(string)
	year, yok := profile["year"].(string)

	if !aok || !tok || !yok {
		c.JSON(http.StatusBadRequest, ErrorMsg{"Invalid request - incomplete profile data"})
		return
	}

	albumInfo := AlbumInfo{
		Artist: artist,
		Title: title,
		Year: year,
	}

	fmt.Println(albumInfo)

	// For this example, we'll respond with a fixed albumID and a placeholder image size
	metaData := ImageMetaData{
		AlbumID:   "fixedAlbumKey12345",
		ImageSize: "2048",
	}
	c.JSON(http.StatusOK, metaData)
}

func isValidAlbumID(albumID string) bool {
	// TODO: valid albumID
	return true
}
